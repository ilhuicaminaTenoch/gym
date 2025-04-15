package com.tlalocalli.gym.persistence.dto.request;

import com.tlalocalli.gym.persistence.enums.EstatusProducto;
import com.tlalocalli.gym.service.validate.producto.UniqueCodigoBarras;
import com.tlalocalli.gym.service.validate.producto.UniqueCodigoBarrasOnUpdate;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
@UniqueCodigoBarrasOnUpdate
public class ProductoUpdateRequest {
    @NotNull(message = "El ID del producto es obligatorio para la actualización")
    private Integer id;

    // Campos opcionales; si no se envían, no se modifican
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    private String descripcion;

    private BigDecimal precio;

    private Integer stock;

    @Size(max = 100, message = "El código de barras no puede exceder 100 caracteres")
    private String codigoBarras;

    private String sku;

    private EstatusProducto estatus;
}
