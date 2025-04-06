package com.tlalocalli.gym.persistence.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SuscripcionResponse {
    private Integer idSuscripcion;
    private Integer idCliente;
    private Integer idPlan;
    private Integer idPromocion; // Opcional
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private String estado;
}
