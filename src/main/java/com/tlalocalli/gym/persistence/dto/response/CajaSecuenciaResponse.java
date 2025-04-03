package com.tlalocalli.gym.persistence.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class CajaSecuenciaResponse {
    private Integer idCaja;
    private LocalDate fecha;
    private Integer secuencia;
}
