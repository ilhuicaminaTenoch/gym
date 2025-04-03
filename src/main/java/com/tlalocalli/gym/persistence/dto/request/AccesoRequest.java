package com.tlalocalli.gym.persistence.dto.request;

import com.tlalocalli.gym.persistence.enums.MetodoAcceso;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AccesoRequest {
    @NotNull(message = "El idCliente es obligatorio")
    private Integer idCliente;

    @NotNull(message = "La fecha de acceso es obligatoria")
    private LocalDateTime fechaAcceso;

    @NotNull(message = "La fecha de salida es obligatoria")
    private LocalDateTime fechaSalida;

    @NotNull(message = "El método de acceso es obligatorio (BIOMETRIA o TARJETA)")
    private MetodoAcceso metodoAcceso;
}
