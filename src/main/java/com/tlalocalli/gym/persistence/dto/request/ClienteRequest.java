package com.tlalocalli.gym.persistence.dto.request;

import com.tlalocalli.gym.service.validate.cliente.UniqueEmail;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;


@Data
public class ClienteRequest {
    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 100, message = "El apellido no puede tener más de 100 caracteres")
    private String apellido;

    @Past(message = "La fecha de nacimiento debe ser una fecha pasada")
    private Date fechaNacimiento;

    @Email(message = "El correo electrónico no es válido")
    @Size(max = 150, message = "El correo electrónico no puede tener más de 150 caracteres")
    @UniqueEmail(message = "El email ya está registrado")
    private String email;

    @Size(max = 50, message = "El teléfono no puede tener más de 50 caracteres")
    private String telefono;

    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
    private String direccion;
}
