package com.tlalocalli.gym.service;


import com.tlalocalli.gym.persistence.dto.request.SuscripcionRequest;
import com.tlalocalli.gym.persistence.dto.response.SuscripcionResponse;
import com.tlalocalli.gym.persistence.entity.ClienteEntity;
import com.tlalocalli.gym.persistence.entity.PlanSuscripcionEntity;
import com.tlalocalli.gym.persistence.entity.PromocionEntity;
import com.tlalocalli.gym.persistence.entity.SuscripcionEntity;
import com.tlalocalli.gym.persistence.repository.ClienteRepository;
import com.tlalocalli.gym.persistence.repository.PlanSuscripcionRepository;
import com.tlalocalli.gym.persistence.repository.PromocionRepository;
import com.tlalocalli.gym.persistence.repository.SuscripcionRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SuscripcionService {

    private final SuscripcionRepository suscripcionRepository;
    private final ClienteRepository clienteRepository;
    private final PlanSuscripcionRepository planSuscripcionRepository;
    private final PromocionRepository promocionRepository;

    @Transactional
    public SuscripcionResponse crearSuscripcion(SuscripcionRequest request) {
        // Buscar y asignar las entidades relacionadas
        ClienteEntity cliente = findCliente(request.getIdCliente());
        PlanSuscripcionEntity plan = findPlan(request.getIdPlan());
        PromocionEntity promocion = (request.getIdPromocion() != null)
                ? findPromocion(request.getIdPromocion())
                : null;

        SuscripcionEntity suscripcion = SuscripcionEntity.builder()
                .cliente(cliente)
                .plan(plan)
                .promocion(promocion)
                .fechaInicio(request.getFechaInicio())
                .fechaFin(request.getFechaFin())
                .estado(request.getEstado())
                .build();

        SuscripcionEntity guardada = suscripcionRepository.save(suscripcion);
        log.info("Suscripción creada con id: {}", guardada.getId());
        return mapToResponse(guardada);
    }

    @Transactional
    public SuscripcionResponse actualizarSuscripcion(Integer id, SuscripcionRequest request) {
        SuscripcionEntity suscripcion = suscripcionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Suscripción no encontrada con id " + id));

        // Actualizar las entidades relacionadas, si se proporcionan nuevos IDs.
        suscripcion.setCliente(findCliente(request.getIdCliente()));
        suscripcion.setPlan(findPlan(request.getIdPlan()));
        if (request.getIdPromocion() != null) {
            suscripcion.setPromocion(findPromocion(request.getIdPromocion()));
        } else {
            suscripcion.setPromocion(null);
        }
        suscripcion.setFechaInicio(request.getFechaInicio());
        suscripcion.setFechaFin(request.getFechaFin());
        suscripcion.setEstado(request.getEstado());

        SuscripcionEntity actualizada = suscripcionRepository.save(suscripcion);
        log.info("Suscripción actualizada con id: {}", actualizada.getId());
        return mapToResponse(actualizada);
    }

    public SuscripcionResponse obtenerSuscripcionPorId(Integer id) {
        SuscripcionEntity suscripcion = suscripcionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Suscripción no encontrada con id " + id));
        return mapToResponse(suscripcion);
    }

    public List<SuscripcionResponse> obtenerTodasSuscripciones() {
        return suscripcionRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void eliminarSuscripcion(Integer id) {
        SuscripcionEntity suscripcion = suscripcionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Suscripción no encontrada con id " + id));
        suscripcionRepository.delete(suscripcion);
    }

    // Métodos privados para búsquedas
    private ClienteEntity findCliente(Integer idCliente) {
        return clienteRepository.findById(idCliente)
                .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado con id " + idCliente));
    }

    private PlanSuscripcionEntity findPlan(Integer idPlan) {
        return planSuscripcionRepository.findById(idPlan)
                .orElseThrow(() -> new EntityNotFoundException("Plan de suscripción no encontrado con id " + idPlan));
    }

    private PromocionEntity findPromocion(Integer idPromocion) {
        return promocionRepository.findById(idPromocion)
                .orElseThrow(() -> new EntityNotFoundException("Promoción no encontrada con id " + idPromocion));
    }

    // Método privado para mapear una SuscripcionEntity a SuscripcionResponse
    private SuscripcionResponse mapToResponse(SuscripcionEntity suscripcion) {
        return SuscripcionResponse.builder()
                .idSuscripcion(suscripcion.getId())
                .idCliente(suscripcion.getCliente() != null ? suscripcion.getCliente().getId() : null)
                .idPlan(suscripcion.getPlan() != null ? suscripcion.getPlan().getId() : null)
                .idPromocion(suscripcion.getPromocion() != null ? suscripcion.getPromocion().getId() : null)
                .fechaInicio(suscripcion.getFechaInicio())
                .fechaFin(suscripcion.getFechaFin())
                .estado(suscripcion.getEstado())
                .build();
    }
}