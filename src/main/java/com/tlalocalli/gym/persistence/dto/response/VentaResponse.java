package com.tlalocalli.gym.persistence.dto.response;

import com.tlalocalli.gym.persistence.enums.MetodoPago;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class VentaResponse {
    private Integer idVenta;
    private Integer idCliente;
    private Integer idUsuario;
    private LocalDateTime fechaVenta;
    private BigDecimal total;
    private MetodoPago metodoPago;
    private List<DetalleVentaResponse> detalles;
    private PagoResponse pago;
}
