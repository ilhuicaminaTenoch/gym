package com.tlalocalli.gym.persistence.dto.response;

import com.tlalocalli.gym.persistence.enums.EstatusProducto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ProductoResponse {
    private Integer id;
    private String nombre;
    private String descripcion;
    private BigDecimal precio;
    private Integer stock;
    private String codigoBarras;
    private String imagen;
    private String sku;
    private EstatusProducto estatus;
}
