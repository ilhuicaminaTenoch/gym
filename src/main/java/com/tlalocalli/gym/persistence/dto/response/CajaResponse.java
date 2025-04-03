package com.tlalocalli.gym.persistence.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CajaResponse {
    private Integer idCaja;
    private String nombre;
    private String prefijo;
}
