package com.tlalocalli.gym.persistence.dto.request;

import com.tlalocalli.gym.persistence.enums.MetodoPago;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PlanSuscripcionRequest {
    private BigDecimal costoBase;
    private String plan;
}
