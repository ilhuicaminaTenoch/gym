package com.tlalocalli.gym.persistence.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class VariacionProductoRequest {
    @NotBlank(message = "El sku es obligatorio")
    private String sku;

    @NotNull(message = "El precio es obligatorio")
    private BigDecimal precio;

    private Integer stock;
    private String imagenUrl;
    private Map<String, String> atributos;
}
