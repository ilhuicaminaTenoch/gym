package com.tlalocalli.gym.persistence.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class AjusteStockRequest {
    @NotEmpty(message = "La lista de items no puede estar vacía")
    private List<ItemAjusteStockRequest> items;
}
