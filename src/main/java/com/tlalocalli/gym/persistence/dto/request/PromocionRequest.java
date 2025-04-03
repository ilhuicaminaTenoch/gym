package com.tlalocalli.gym.persistence.dto.request;

import com.tlalocalli.gym.persistence.enums.TipoPlan;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PromocionRequest {
    @Size(max = 255, message = "La descripción no puede superar 255 caracteres")
    private String descripcion;

    @DecimalMin(value = "0.0", message = "El descuento no puede ser negativo")
    private BigDecimal descuento;

    @NotNull(message = "El tipo de plan es obligatorio (MENSUAL, SEMESTRAL o ANUAL)")
    private TipoPlan tipoPlan;

    private LocalDateTime vigenciaInicio;
    private LocalDateTime vigenciaFin;
}
