package com.tlalocalli.gym.service.validate.cliente;

import com.tlalocalli.gym.persistence.repository.ClienteRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> {
    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (email == null || email.isBlank()) {
            // Si está vacío o nulo, que se encarguen otras validaciones (ej: @NotBlank).
            return true;
        }
        return !clienteRepository.existsByEmail(email);
    }
}
