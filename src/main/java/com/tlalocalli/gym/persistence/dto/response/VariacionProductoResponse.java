package com.tlalocalli.gym.persistence.dto.response;

import lombok.Builder;
import lombok.Data;
import java.util.Map;

@Data
@Builder
public class VariacionProductoResponse {
    private Long id; // El ID de la VariacionProductoEntity
    private Map<String, String> atributos;
    // Futuros campos como skuVariacion, precioVariacion podrían añadirse aquí si es necesario.
}
