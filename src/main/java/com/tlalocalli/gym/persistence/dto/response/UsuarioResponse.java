package com.tlalocalli.gym.persistence.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponse {
    private Integer id;
    private String username;
    private String email;
    private String role;
    private String estado;
}
