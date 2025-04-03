package com.tlalocalli.gym.persistence.dto.response;

import com.tlalocalli.gym.persistence.enums.TipoPlan;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PromocionResponse {
    private Integer id;
    private String descripcion;
    private BigDecimal descuento;
    private TipoPlan tipoPlan;
    private LocalDateTime vigenciaInicio;
    private LocalDateTime vigenciaFin;
}
