package com.tlalocalli.gym.service;

import com.tlalocalli.gym.persistence.dto.request.CajaRequest;
import com.tlalocalli.gym.persistence.dto.response.CajaResponse;
import com.tlalocalli.gym.persistence.entity.CajaEntity;
import com.tlalocalli.gym.persistence.repository.CajaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CajaService {

    private final CajaRepository cajaRepository;

    @Transactional
    public CajaResponse createCaja(CajaRequest request) {
        CajaEntity caja = CajaEntity.builder()
                .nombre(request.getNombre())
                .prefijo(request.getPrefijo() != null ? request.getPrefijo() : "CASH")
                .build();
        caja = cajaRepository.save(caja);

        return CajaResponse.builder()
                .idCaja(caja.getId())
                .nombre(caja.getNombre())
                .prefijo(caja.getPrefijo())
                .build();
    }
}
