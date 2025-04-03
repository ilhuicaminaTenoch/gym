package com.tlalocalli.gym.persistence.dto.request;

import com.tlalocalli.gym.service.validate.cliente.UniqueEmailOnUpdate;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
@UniqueEmailOnUpdate
public class ClienteUpdateRequest {
    // Solo el ID es obligatorio para identificar al cliente a actualizar
    @NotNull(message = "El ID del cliente es obligatorio para la actualización")
    private Integer id;

    // El resto de campos son opcionales,
    // pero si se proporcionan, se validan (tamaño, formato, etc.)
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String nombre;

    @Size(max = 100, message = "El apellido no puede tener más de 100 caracteres")
    private String apellido;

    private Date fechaNacimiento;

    @Email(message = "El correo electrónico no es válido")
    @Size(max = 150, message = "El correo electrónico no puede tener más de 150 caracteres")
    private String email;

    @Size(max = 50, message = "El teléfono no puede tener más de 50 caracteres")
    private String telefono;

    @Size(max = 255, message = "La dirección no puede tener más de 255 caracteres")
    private String direccion;
}
