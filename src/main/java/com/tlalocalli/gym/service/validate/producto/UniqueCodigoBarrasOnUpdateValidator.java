package com.tlalocalli.gym.service.validate.producto;

import com.tlalocalli.gym.persistence.entity.ProductoEntity;
import com.tlalocalli.gym.persistence.repository.ProductoRepository;
import com.tlalocalli.gym.persistence.dto.request.ProductoUpdateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueCodigoBarrasOnUpdateValidator implements
        ConstraintValidator<UniqueCodigoBarrasOnUpdate, ProductoUpdateRequest> {

    private static final Logger log = LoggerFactory.getLogger(UniqueCodigoBarrasOnUpdateValidator.class);
    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public boolean isValid(ProductoUpdateRequest request, ConstraintValidatorContext context) {
        // Si no hay ID, no podemos validar en modo "update"
        if (request.getId() == null) {
            return true;
        }

        // Obtener el producto existente
        Optional<ProductoEntity> existingProductOpt = productoRepository.findById(request.getId());
        if (existingProductOpt.isEmpty()) {
            // Si no existe, no validamos; en tu caso podrías lanzar excepción
            return true;
        }

        ProductoEntity existingProduct = existingProductOpt.get();

        // Si no viene un codigo de barras nuevo o es igual al actual, no hay cambio
        if (request.getCodigoBarras() == null || request.getCodigoBarras().equals(existingProduct.getCodigoBarras())) {
            return true;
        }

        // Si el codigo de barras es distinto, verificamos si ya está en uso
        return !productoRepository.existsByCodigoBarras(request.getCodigoBarras());
    }
}
