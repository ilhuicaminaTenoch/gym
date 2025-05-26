package com.tlalocalli.gym.persistence.dto.request;

import com.tlalocalli.gym.persistence.enums.EstatusProducto;
import com.tlalocalli.gym.service.validate.producto.UniqueCodigoBarras;
// No es necesario importar jakarta.persistence.EnumType y jakarta.persistence.Enumerated aquí
// ya que no se usan directamente como anotaciones en los campos de este DTO.
// EstatusProducto es un enum, y su manejo JPA estaría en la entidad.
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ProductoRequest {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;

    @Size(max = 255, message = "La descripción no puede exceder 255 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    private BigDecimal precio;

    @NotNull(message = "El stock es obligatorio")
    private Integer stock;

    @NotBlank(message = "El código de barras es obligatorio")
    @Size(max = 100, message = "El código de barras no puede exceder 100 caracteres")
    @UniqueCodigoBarras(message = "El código de barras ya está registrado")
    private String codigoBarras;

    @NotNull(message = "El SKU es obligatorio")
    private String sku;

    private EstatusProducto estatus; // Asumo que EstatusProducto es un enum definido en tu proyecto

    @Valid // Para que se validen los objetos VariacionProductoRequest dentro de la lista
    private List<VariacionProductoRequest> variaciones = new ArrayList<>();

    private String variacionesJson; // Nuevo campo añadido
}
