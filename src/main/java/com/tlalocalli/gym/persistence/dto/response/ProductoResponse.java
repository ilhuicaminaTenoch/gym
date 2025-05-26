package com.tlalocalli.gym.persistence.dto.response;

import com.tlalocalli.gym.persistence.enums.EstatusProducto;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
// Se elimina la importación de LocalDateTime ya que no se usa en este DTO
import java.util.List; // Nueva importación
import com.tlalocalli.gym.persistence.dto.response.VariacionProductoResponse; // Nueva importación

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
    private List<VariacionProductoResponse> variaciones; // Nuevo campo
}
