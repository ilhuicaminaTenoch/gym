package com.tlalocalli.gym.service.validate.producto;


import com.tlalocalli.gym.persistence.repository.ProductoRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueCodigoBarrasValidator implements ConstraintValidator<UniqueCodigoBarras, String> {
    private final ProductoRepository productoRepository;

    @Override
    public boolean isValid(String codigoBarras, ConstraintValidatorContext context) {
        if (codigoBarras == null || codigoBarras.isBlank()) {
            return true;
        }
        // Retorna false si ya existe un producto con ese código
        return !productoRepository.existsByCodigoBarras(codigoBarras);
    }
}
