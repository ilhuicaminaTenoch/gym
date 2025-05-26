package com.tlalocalli.gym.persistence.dto.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VariacionProductoRequest {

    @NotEmpty(message = "Los atributos no pueden estar vacíos.")
    private Map<String, String> atributos;
}
