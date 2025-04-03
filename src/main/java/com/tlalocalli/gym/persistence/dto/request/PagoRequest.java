package com.tlalocalli.gym.persistence.dto.request;

import com.tlalocalli.gym.persistence.enums.MetodoPago;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;


import java.math.BigDecimal;

@Data
@Builder
public class PagoRequest {
    @NotNull(message = "El monto es obligatorio")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0")
    private BigDecimal monto;

    @NotNull(message = "El método de pago es obligatorio")
    private MetodoPago metodoPago;

    @Size(max = 50, message = "El número de transacción no puede exceder 50 caracteres")
    private String numTransaccion;

    @PositiveOrZero(message = "El ID de promoción debe ser positivo o cero")
    private Integer idPromocion;
}
