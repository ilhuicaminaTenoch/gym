package com.tlalocalli.gym.persistence.dto.request;

import com.tlalocalli.gym.persistence.enums.MetodoPago;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PagoSuscripcionRequest {
    private Integer idSuscripcion;
    private BigDecimal monto;
    private MetodoPago metodoPago;
    private String numTransaccion;
    private Integer idPromocion;
    private Integer idCaja;
}
