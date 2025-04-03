package com.tlalocalli.gym.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteFilterDto {
    private String nombre;
    private String email;
    private String telefono;
}
