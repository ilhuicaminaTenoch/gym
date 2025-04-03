package com.tlalocalli.gym.persistence.dto.request;

import com.tlalocalli.gym.persistence.enums.MetodoPago;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class VentaRequest {
    @NotNull(message = "El ID del cliente es obligatorio")
    @Positive(message = "El ID del cliente debe ser un número positivo")
    private Integer idCliente;

    @NotNull(message = "El ID del usuario es obligatorio")
    @Positive(message = "El ID del usuario debe ser un número positivo")
    private Integer idUsuario;


    @NotNull(message = "El método de pago es obligatorio (TARJETA, EFECTIVO o ELECTONICO)")
    private MetodoPago metodoPago;


    @NotNull(message = "Los detalles de la venta son obligatorios")
    @Size(min = 1, message = "Debe haber al menos un detalle de venta")
    @Valid
    private List<DetalleVentaRequest> detalles;

    @NotNull(message = "Los datos del pago son obligatorios")
    @Valid
    private PagoRequest pago;

    @NotNull(message = "El ID de la caja es obligatorio")
    @Positive(message = "El ID de la caja debe ser un número positivo")
    private Integer idCaja;
}
