package com.tlalocalli.gym.persistence.dto.request;

import com.tlalocalli.gym.persistence.enums.MetodoPago;
import com.tlalocalli.gym.persistence.enums.TipoVenta;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class VentaRequest {
    @Positive(message = "El ID del cliente debe ser un número positivo")
    private Integer idCliente;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Positive(message = "El ID del usuario debe ser un número positivo")
    private Integer idUsuario;

    @NotNull(message = "El ID de la caja es obligatorio")
    @Positive(message = "El ID de la caja debe ser un número positivo")
    private Integer idCaja;

    @Positive(message = "El ID de suscripcion debe ser un número positivo")
    private Integer idSuscripcion;

    @NotNull(message = "El método de pago es obligatorio (TARJETA, EFECTIVO o ELECTONICO)")
    private MetodoPago metodoPago;

    private List<DetalleVentaRequest> detalles;
}
