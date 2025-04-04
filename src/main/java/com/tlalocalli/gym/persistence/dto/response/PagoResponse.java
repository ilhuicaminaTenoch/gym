package com.tlalocalli.gym.persistence.dto.response;

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
    private String metodoPago;
    private String numTransaccion;
    private Integer idPromocion;
}
