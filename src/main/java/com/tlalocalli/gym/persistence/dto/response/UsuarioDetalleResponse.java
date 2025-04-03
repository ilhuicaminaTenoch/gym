package com.tlalocalli.gym.persistence.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UsuarioDetalleResponse {
    private Integer id;
    private String nombreCompleto;
    private String direccion;
    private String telefono;
    private LocalDateTime fechaNacimiento;
    // Se retorna como String para facilitar su consumo
    private String genero;
}