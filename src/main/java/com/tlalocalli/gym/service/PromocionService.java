package com.tlalocalli.gym.service;

import com.tlalocalli.gym.persistence.dto.PromocionFilterDto;
import com.tlalocalli.gym.persistence.entity.PromocionEntity;
import com.tlalocalli.gym.persistence.repository.PromocionRepository;
import com.tlalocalli.gym.persistence.dto.request.PromocionRequest;
import com.tlalocalli.gym.persistence.dto.response.PromocionResponse;
import com.tlalocalli.gym.util.PromocionSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PromocionService {

    private final PromocionRepository promocionRepository;

    public PromocionResponse createPromocion(PromocionRequest request) {
        log.info("Creando nueva promoción: {}", request);

        PromocionEntity entity = new PromocionEntity();
        entity.setDescripcion(request.getDescripcion());
        entity.setDescuento(request.getDescuento());
        entity.setTipoPlan(request.getTipoPlan());
        entity.setVigenciaInicio(request.getVigenciaInicio());
        entity.setVigenciaFin(request.getVigenciaFin());

        entity = promocionRepository.save(entity);

        return mapToResponse(entity);
    }

    public PromocionResponse updatePromocion(Integer id, PromocionRequest request) {
        log.info("Actualizando promoción con id: {}", id);

        PromocionEntity existing = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada con id: " + id));

        // Solo actualizamos campos que vengan con valor
        if (request.getDescripcion() != null) {
            existing.setDescripcion(request.getDescripcion());
        }
        if (request.getDescuento() != null) {
            existing.setDescuento(request.getDescuento());
        }
        if (request.getTipoPlan() != null) {
            existing.setTipoPlan(request.getTipoPlan());
        }
        if (request.getVigenciaInicio() != null) {
            existing.setVigenciaInicio(request.getVigenciaInicio());
        }
        if (request.getVigenciaFin() != null) {
            existing.setVigenciaFin(request.getVigenciaFin());
        }

        existing = promocionRepository.save(existing);
        return mapToResponse(existing);
    }

    public PromocionResponse getPromocionById(Integer id) {
        PromocionEntity entity = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada con id: " + id));
        return mapToResponse(entity);
    }

    public Page<PromocionResponse> getAllPromociones(Pageable pageable) {
        return promocionRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public void deletePromocion(Integer id) {
        PromocionEntity existing = promocionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Promoción no encontrada con id: " + id));
        promocionRepository.delete(existing);
    }


    private PromocionResponse mapToResponse(PromocionEntity entity) {
        return PromocionResponse.builder()
                .id(entity.getId())
                .descripcion(entity.getDescripcion())
                .descuento(entity.getDescuento())
                .tipoPlan(entity.getTipoPlan())
                .vigenciaInicio(entity.getVigenciaInicio())
                .vigenciaFin(entity.getVigenciaFin())
                .build();
    }

    public Page<PromocionResponse> getPromociones(PromocionFilterDto filter, Pageable pageable) {
        // Construir la Specification según los filtros enviados
        Specification<PromocionEntity> spec = PromocionSpecification.filter(filter);

        // Obtener la página de entidades según la Specification
        Page<PromocionEntity> entities = promocionRepository.findAll(spec, pageable);

        // Mapear las entidades a DTOs de respuesta
        List<PromocionResponse> dtoList = entities.getContent().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, entities.getTotalElements());
    }

}
