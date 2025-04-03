package com.tlalocalli.gym.persistence.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CajaRequest {
    private String nombre;
    private String prefijo;
}
