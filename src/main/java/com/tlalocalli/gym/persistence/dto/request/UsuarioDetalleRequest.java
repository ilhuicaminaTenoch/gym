package com.tlalocalli.gym.persistence.dto.request;

import com.tlalocalli.gym.persistence.enums.Genero;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UsuarioDetalleRequest {

    @NotBlank(message = "El nombre completo es obligatorio")
    private String nombreCompleto;

    // Opcionales según tu lógica de negocio
    private String direccion;
    private String telefono;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    private LocalDateTime fechaNacimiento;

    @NotNull(message = "El género es obligatorio")
    private Genero genero;
}
