package com.tlalocalli.gym.persistence.dto.response;

import com.tlalocalli.gym.persistence.enums.MetodoAcceso;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AccesoResponse {
    private Integer id;
    private Integer idCliente;
    private LocalDateTime fechaAcceso;
    private LocalDateTime fechaSalida;
    private MetodoAcceso metodoAcceso;
}
