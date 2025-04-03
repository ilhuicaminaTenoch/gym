package com.tlalocalli.gym.service.validate.cliente;

import com.tlalocalli.gym.persistence.entity.ClienteEntity;
import com.tlalocalli.gym.persistence.repository.ClienteRepository;
import com.tlalocalli.gym.persistence.dto.request.ClienteUpdateRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UniqueEmailOnUpdateValidator implements
        ConstraintValidator<UniqueEmailOnUpdate, ClienteUpdateRequest> {

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    public boolean isValid(ClienteUpdateRequest request, ConstraintValidatorContext context) {
        // Si no hay ID, no podemos validar en modo "update"
        if (request.getId() == null) {
            return true;
        }

        // Obtener el cliente existente
        Optional<ClienteEntity> existingClienteOpt = clienteRepository.findById(request.getId());
        if (existingClienteOpt.isEmpty()) {
            // Si no existe, no validamos; en tu caso podrías lanzar excepción
            return true;
        }

        ClienteEntity existingCliente = existingClienteOpt.get();

        // Si no viene un email nuevo o es igual al actual, no hay cambio
        if (request.getEmail() == null || request.getEmail().equals(existingCliente.getEmail())) {
            return true;
        }

        // Si el email es distinto, verificamos si ya está en uso
        return !clienteRepository.existsByEmail(request.getEmail());
    }
}
