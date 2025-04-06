package com.tlalocalli.gym.service;

import com.tlalocalli.gym.persistence.dto.request.DetalleVentaRequest;
import com.tlalocalli.gym.persistence.dto.request.VentaRequest;
import com.tlalocalli.gym.persistence.dto.response.DetalleVentaResponse;
import com.tlalocalli.gym.persistence.dto.response.VentaResponse;
import com.tlalocalli.gym.persistence.entity.*;
import com.tlalocalli.gym.persistence.enums.MetodoPago;
import com.tlalocalli.gym.persistence.enums.TipoVenta;
import com.tlalocalli.gym.persistence.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    private final PromocionRepository promocionRepository;
    private final CajaRepository cajaRepository;
    private final SuscripcionRepository suscripcionRepository;

    @Transactional
    public VentaResponse crearVenta(VentaRequest request) {
        ClienteEntity cliente = null;
        SuscripcionEntity suscripcionVenta = null;
        PlanSuscripcionEntity planSuscripcion = null;
        TipoVenta tipoVenta = null;

        if (request.getIdCliente() != null) {
            cliente = findCliente(request.getIdCliente());
        }
        UsuarioEntity usuario = findUsuario(request.getIdUsuario());
        CajaEntity caja = findCaja(request.getIdCaja());
        TurnoEntity turno = findActiveTurno(request.getIdUsuario());


        if (request.getIdSuscripcion() != null) {
            suscripcionVenta = findSuscripcion(request.getIdSuscripcion());
            planSuscripcion = PlanSuscripcionEntity.builder()
                    .id(suscripcionVenta.getId())
                    .costoBase(suscripcionVenta.getPlan().getCostoBase())
                    .build();
        }

        // Procesar detalles: construir lista y calcular total
        VentaDetailData detailData = buildDetalleVentas(request.getDetalles());

        if (request.getIdSuscripcion() != null && !detailData.getDetalles().isEmpty()) {
            tipoVenta = TipoVenta.MIXTO;
        } else if (request.getIdSuscripcion() != null) {
            tipoVenta = TipoVenta.SUSCRIPCION;
        } else if (!detailData.getDetalles().isEmpty()) {
            tipoVenta = TipoVenta.PRODUCTO;
        }


        if (suscripcionVenta != null && planSuscripcion != null) {
            BigDecimal nuevoTotal = detailData.getTotal().add(planSuscripcion.getCostoBase());
            detailData = new VentaDetailData(nuevoTotal, detailData.getDetalles());
        }



        // Crear y persistir la venta
        VentaEntity ventaEntity = createAndSaveVenta(
                usuario,
                turno,
                caja,
                suscripcionVenta,
                tipoVenta,
                detailData.getTotal(),
                request.getMetodoPago(),
                cliente,
                detailData.getDetalles()
        );

        // Convertir detalles a DTO
        List<DetalleVentaResponse> detallesResponse = convertDetallesToResponse(ventaEntity.getDetalleVentas(), planSuscripcion);

        // Construir y retornar la respuesta final
        return VentaResponse.builder()
                .idVenta(ventaEntity.getId())
                .idCliente(cliente != null ? cliente.getId() : null)
                .idUsuario(usuario.getId())
                .fechaVenta(ventaEntity.getFechaVenta())
                .metodoPago(ventaEntity.getMetodoPago())
                .total(ventaEntity.getTotal())
                .detalles(detallesResponse)
                .build();
    }

    private SuscripcionEntity findSuscripcion(Integer idSuscripcion) {
        return suscripcionRepository.findByPlanSuscripcionId(idSuscripcion);
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
     * y calcular el total de la venta. Si el detalle contiene idProducto se trata de un producto;
     * si contiene idSuscripcion se procesa como detalle de suscripción.
     */
    private VentaDetailData buildDetalleVentas(List<DetalleVentaRequest> detalleRequests) {
        BigDecimal total = BigDecimal.ZERO;
        List<DetalleVentaEntity> detalles = new ArrayList<>();
        if (!detalleRequests.isEmpty()) {
            for (DetalleVentaRequest detReq : detalleRequests) {
                // Procesar detalle de producto
                if (detReq.getIdProducto() != null) {
                    ProductoEntity producto = productoRepository.findById(detReq.getIdProducto())
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
                } else {
                    throw new IllegalArgumentException("Cada detalle debe contener idProducto o idSuscripcion");
                }
            }
        }
        return new VentaDetailData(total, detalles);
    }

    private VentaEntity createAndSaveVenta(UsuarioEntity usuario,
                                           TurnoEntity turno,
                                           CajaEntity caja,
                                           SuscripcionEntity suscripcion,
                                           TipoVenta tipoVenta,
                                           BigDecimal total,
                                           MetodoPago metodoPago,
                                           ClienteEntity cliente,
                                           List<DetalleVentaEntity> detalles) {
        LocalDateTime now = LocalDateTime.now();
        VentaEntity venta = VentaEntity.builder()
                .usuario(usuario)
                .turno(turno)
                .caja(caja)
                .suscripcion(suscripcion)
                .fechaVenta(now)
                .metodoPago(metodoPago)
                .cliente(cliente)
                .tipoVenta(tipoVenta != null ? tipoVenta : TipoVenta.PRODUCTO)
                .total(total)
                .detalleVentas(detalles)
                .build();
        detalles.forEach(detalle -> detalle.setVenta(venta));
        return ventaRepository.save(venta);
    }

    private List<DetalleVentaResponse> convertDetallesToResponse(List<DetalleVentaEntity> detalles, PlanSuscripcionEntity planSuscripcion) {
        List<DetalleVentaResponse> lista = detalles.stream()
                .map(detalle -> DetalleVentaResponse.builder()
                        .idDetalle(detalle.getId())
                        .idProducto(detalle.getProducto().getId())
                        .cantidad(detalle.getCantidad())
                        .precioUnitario(detalle.getPrecioUnitario())
                        .subtotal(detalle.getSubtotal())
                        .build())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (planSuscripcion != null) {
            DetalleVentaResponse detalleSuscripcion = DetalleVentaResponse.builder()
                    .idDetalle(null)
                    .idSuscripcion(planSuscripcion.getId())
                    .cantidad(1)
                    .precioUnitario(planSuscripcion.getCostoBase())
                    .subtotal(planSuscripcion.getCostoBase())
                    .build();
            lista.add(detalleSuscripcion);
        }
        return lista;
    }


    // Clase auxiliar para encapsular el total y la lista de detalles
    @Getter
    private static class VentaDetailData {
        private final BigDecimal total;
        private final List<DetalleVentaEntity> detalles;

        public VentaDetailData(BigDecimal total, List<DetalleVentaEntity> detalles) {
            this.total = total;
            this.detalles = detalles;
        }

    }
}
