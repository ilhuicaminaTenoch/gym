package com.tlalocalli.gym.service;

import com.tlalocalli.gym.persistence.dto.request.DetalleVentaRequest;
import com.tlalocalli.gym.persistence.dto.request.PagoRequest;
import com.tlalocalli.gym.persistence.dto.request.VentaRequest;
import com.tlalocalli.gym.persistence.dto.response.DetalleVentaResponse;
import com.tlalocalli.gym.persistence.dto.response.PagoResponse;
import com.tlalocalli.gym.persistence.dto.response.VentaResponse;
import com.tlalocalli.gym.persistence.entity.*;
import com.tlalocalli.gym.persistence.enums.MetodoPago;
import com.tlalocalli.gym.persistence.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class VentaService {

    private final VentaRepository ventaRepository;
    private final DetalleVentaRepository detalleVentaRepository;
    private final ClienteRepository clienteRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProductoRepository productoRepository;
    private final TurnoRepository turnoRepository;
    private final PagoRepository pagoRepository;
    private final PromocionRepository promocionRepository;
    private final CajaRepository cajaRepository;

    @Transactional
    public VentaResponse crearVenta(VentaRequest request) {
        // Obtener entidades: cliente, usuario, caja y turno activo
        ClienteEntity cliente = findCliente(request.getIdCliente());
        UsuarioEntity usuario = findUsuario(request.getIdUsuario());
        CajaEntity caja = findCaja(request.getIdCaja());
        TurnoEntity turno = findActiveTurno(request.getIdUsuario());

        // Procesar detalles: construir lista y calcular total
        VentaDetailData detailData = buildDetalleVentas(request.getDetalles());

        // Crear y persistir la venta
        VentaEntity ventaEntity = createAndSaveVenta(cliente, usuario, turno, request.getMetodoPago(), detailData.getTotal(), detailData.getDetalles());

        // Convertir detalles a DTO
        List<DetalleVentaResponse> detallesResponse = convertDetallesToResponse(ventaEntity.getDetalleVentas());

        // Registrar el pago y obtener su respuesta
        PagoResponse pagoResponse = registrarPago(ventaEntity, request.getPago(), caja);

        // Construir y retornar la respuesta final
        return VentaResponse.builder()
                .idVenta(ventaEntity.getId())
                .idCliente(cliente.getId())
                .idUsuario(usuario.getId())
                .fechaVenta(ventaEntity.getFechaVenta())
                .metodoPago(ventaEntity.getMetodoPago())
                .total(ventaEntity.getTotal())
                .detalles(detallesResponse)
                .pago(pagoResponse)
                .build();
    }

    private ClienteEntity findCliente(Integer idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
    }

    private UsuarioEntity findUsuario(Integer idUsuario) {
        return usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));
    }

    private CajaEntity findCaja(Integer idCaja) {
        return cajaRepository.findById(idCaja)
                .orElseThrow(() -> new EntityNotFoundException("Caja no encontrada"));
    }

    private TurnoEntity findActiveTurno(Integer idUsuario) {
        LocalTime now = LocalTime.now();
        return turnoRepository
                .findByUsuarioIdAndHoraInicioLessThanEqualAndHoraFinGreaterThanEqual(idUsuario, now, now)
                .orElseThrow(() -> new RuntimeException("No se encontró turno activo para el usuario"));
    }

    /**
     * Procesa la lista de DetalleVentaRequest para construir la lista de entidades DetalleVentaEntity
     * y calcular el total de la venta.
     */
    private VentaDetailData buildDetalleVentas(List<DetalleVentaRequest> detalleRequests) {
        BigDecimal total = BigDecimal.ZERO;
        List<DetalleVentaEntity> detalles = new ArrayList<>();
        for (DetalleVentaRequest detReq : detalleRequests) {
            var producto = productoRepository.findById(detReq.getIdProducto())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
            BigDecimal subtotal = detReq.getPrecioUnitario().multiply(BigDecimal.valueOf(detReq.getCantidad()));
            total = total.add(subtotal);

            DetalleVentaEntity detalle = DetalleVentaEntity.builder()
                    .producto(producto)
                    .cantidad(detReq.getCantidad())
                    .precioUnitario(detReq.getPrecioUnitario())
                    .subtotal(subtotal)
                    .build();
            detalles.add(detalle);
        }
        return new VentaDetailData(total, detalles);
    }

    private VentaEntity createAndSaveVenta(ClienteEntity cliente, UsuarioEntity usuario, TurnoEntity turno, MetodoPago metodoPago, BigDecimal total, List<DetalleVentaEntity> detalles) {
        LocalDateTime now = LocalDateTime.now();
        VentaEntity venta = VentaEntity.builder()
                .cliente(cliente)
                .usuario(usuario)
                .turno(turno)
                .fechaVenta(now)
                .metodoPago(metodoPago)
                .total(total)
                .detalleVentas(detalles)
                .build();

        // Establece la relación bidireccional
        detalles.forEach(detalle -> detalle.setVenta(venta));

        return ventaRepository.save(venta);
    }

    private List<DetalleVentaResponse> convertDetallesToResponse(List<DetalleVentaEntity> detalles) {
        return detalles.stream()
                .map(detalle -> DetalleVentaResponse.builder()
                        .idDetalle(detalle.getId())
                        .idProducto(detalle.getProducto().getId())
                        .cantidad(detalle.getCantidad())
                        .precioUnitario(detalle.getPrecioUnitario())
                        .subtotal(detalle.getSubtotal())
                        .build())
                .collect(Collectors.toList());
    }

    public PagoResponse registrarPago(VentaEntity ventaEntity, PagoRequest pagoReq, CajaEntity caja) {
        PromocionEntity promocion = null;
        if (pagoReq.getIdPromocion() != null) {
            promocion = promocionRepository.findById(pagoReq.getIdPromocion())
                    .orElseThrow(() -> new RuntimeException("Promocion no encontrada"));
        }

        PagoEntity pagoEntity = PagoEntity.builder()
                .venta(ventaEntity)
                .monto(pagoReq.getMonto())
                .fechaPago(LocalDateTime.now())
                .metodoPago(pagoReq.getMetodoPago())
                .numTransaccion(pagoReq.getNumTransaccion())
                .promocion(promocion)
                .caja(caja)
                .build();
        pagoEntity = pagoRepository.save(pagoEntity);

        return PagoResponse.builder()
                .idPago(pagoEntity.getId())
                .monto(pagoEntity.getMonto())
                .fechaPago(pagoEntity.getFechaPago())
                .metodoPago(pagoEntity.getMetodoPago())
                .numTransaccion(pagoEntity.getNumTransaccion())
                .idPromocion(pagoEntity.getPromocion() != null ? pagoEntity.getPromocion().getId() : null)
                .build();
    }

    // Clase auxiliar para encapsular el total y la lista de detalles
    private static class VentaDetailData {
        private final BigDecimal total;
        private final List<DetalleVentaEntity> detalles;

        public VentaDetailData(BigDecimal total, List<DetalleVentaEntity> detalles) {
            this.total = total;
            this.detalles = detalles;
        }

        public BigDecimal getTotal() {
            return total;
        }

        public List<DetalleVentaEntity> getDetalles() {
            return detalles;
        }
    }
}
