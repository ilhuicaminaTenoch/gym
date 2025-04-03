package com.tlalocalli.gym.service;

import com.tlalocalli.gym.persistence.dto.request.PagoSuscripcionRequest;
import com.tlalocalli.gym.persistence.dto.response.PagoSuscripcionResponse;
import com.tlalocalli.gym.persistence.entity.CajaEntity;
import com.tlalocalli.gym.persistence.entity.PagoEntity;
import com.tlalocalli.gym.persistence.entity.PromocionEntity;
import com.tlalocalli.gym.persistence.entity.SuscripcionEntity;
import com.tlalocalli.gym.persistence.repository.CajaRepository;
import com.tlalocalli.gym.persistence.repository.PagoRepository;
import com.tlalocalli.gym.persistence.repository.PromocionRepository;
import com.tlalocalli.gym.persistence.repository.SuscripcionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PagoService {

    private final PagoRepository pagoRepository;
    private final SuscripcionRepository suscripcionRepository;
    private final PromocionRepository promocionRepository;
    private final CajaRepository cajaRepository;

    @Transactional
    public PagoSuscripcionResponse registrarPagoSuscripcion(PagoSuscripcionRequest request) {
        // 1. Búsqueda de entidades con excepciones específicas
        SuscripcionEntity suscripcion = getSuscripcionById(request.getIdSuscripcion());
        PromocionEntity promocion = getPromocionById(request.getIdPromocion());
        CajaEntity caja = getCajaById(request.getIdCaja());

        // 2. Construcción del pago usando patrón builder mejorado
        PagoEntity pago = buildPagoEntity(request, suscripcion, promocion, caja);

        // 3. Persistencia
        PagoEntity savedPago = pagoRepository.save(pago);

        // 4. Construcción de respuesta
        return buildResponse(savedPago, suscripcion);
    }

    private PagoEntity buildPagoEntity(PagoSuscripcionRequest request,
                                       SuscripcionEntity suscripcion,
                                       PromocionEntity promocion,
                                       CajaEntity caja) {
        return PagoEntity.builder()
                .suscripcion(suscripcion)
                .monto(request.getMonto())
                .fechaPago(LocalDateTime.now())
                .metodoPago(request.getMetodoPago())
                .caja(caja)
                .numTransaccion(request.getNumTransaccion()) // Si es null, no se setea
                .promocion(promocion)
                .build();
    }

    private SuscripcionEntity getSuscripcionById(Integer id) {
        return suscripcionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Suscripción no encontrada con ID: " + id));
    }

    private PromocionEntity getPromocionById(Integer id) {
        if (id == null) return null;

        return promocionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Promoción no encontrada con ID: " + id));
    }

    private CajaEntity getCajaById(Integer id) {
        if (id == null) return null;

        return cajaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Caja no encontrada con ID: " + id));
    }

    private PagoSuscripcionResponse buildResponse(PagoEntity pago, SuscripcionEntity suscripcion) {
        return new PagoSuscripcionResponse(
                pago.getId(),
                suscripcion.getId(),
                pago.getMonto(),
                pago.getFechaPago(),
                pago.getMetodoPago(),
                pago.getNumTransaccion(),
                Optional.ofNullable(pago.getPromocion())
                        .map(PromocionEntity::getId)
                        .orElse(null)
        );
    }
}
