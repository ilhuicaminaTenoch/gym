package com.tlalocalli.gym.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductoFilterDto {
    private String nombre;
    private String codigoBarras;
}
