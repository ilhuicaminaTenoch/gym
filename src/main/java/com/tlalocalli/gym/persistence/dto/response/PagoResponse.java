package com.tlalocalli.gym.persistence.dto.response;

import com.tlalocalli.gym.persistence.enums.MetodoPago;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PagoResponse {
    private Integer idPago;
    private BigDecimal monto;
    private LocalDateTime fechaPago;
    private MetodoPago metodoPago;
    private String numTransaccion;
    private Integer idPromocion; // si aplica
}
