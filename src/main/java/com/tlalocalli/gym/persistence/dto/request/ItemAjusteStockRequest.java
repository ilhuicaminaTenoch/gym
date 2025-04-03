package com.tlalocalli.gym.persistence.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemAjusteStockRequest {
    @NotNull(message = "El id del producto es obligatorio")
    private Integer idProducto;

    @NotNull(message = "El ajuste de stock es obligatorio")
    private Integer ajuste;
}
