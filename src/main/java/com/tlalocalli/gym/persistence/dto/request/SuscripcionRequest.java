package com.tlalocalli.gym.persistence.dto.request;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SuscripcionRequest {

    @NotNull(message = "El id del cliente es obligatorio")
    @Positive(message = "El id del cliente debe ser un número positivo")
    private Integer idCliente;

    @NotNull(message = "El id del plan es obligatorio")
    @Positive(message = "El id del plan debe ser un número positivo")
    private Integer idPlan;

    // Campo opcional
    @Positive(message = "El id de la promoción debe ser un número positivo")
    private Integer idPromocion;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    private LocalDateTime fechaFin;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    // Validación a nivel de clase para asegurar que fechaFin sea posterior a fechaInicio
    @AssertTrue(message = "La fecha de fin debe ser posterior a la fecha de inicio")
    public boolean isFechaValida() {
        if (fechaInicio == null || fechaFin == null) {
            return true; // NotNull se encargará de validar estos campos
        }
        return fechaFin.isAfter(fechaInicio);
    }
}
