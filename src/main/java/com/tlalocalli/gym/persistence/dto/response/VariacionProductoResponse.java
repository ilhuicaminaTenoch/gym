package com.tlalocalli.gym.persistence.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class VariacionProductoResponse {
    private Integer id;
    private String sku;
    private BigDecimal precio;
    private Integer stock;
    private String imagenUrl;
    private Map<String, String> atributos;
}
