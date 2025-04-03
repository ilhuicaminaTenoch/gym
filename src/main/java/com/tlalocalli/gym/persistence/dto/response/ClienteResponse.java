package com.tlalocalli.gym.persistence.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class ClienteResponse {
    private Integer id;
    private String nombre;
    private String apellido;
    private Date fechaNacimiento;
    private String email;
    private String telefono;
    private String direccion;
    private LocalDateTime fechaRegistro;
}
