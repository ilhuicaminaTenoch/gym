package com.tlalocalli.gym.util;

import com.tlalocalli.gym.persistence.dto.ClienteFilterDto;
import com.tlalocalli.gym.persistence.entity.ClienteEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ClienteSpecification {

    private ClienteSpecification() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Specification<ClienteEntity> hasEmail(String email) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            if (email == null || email.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            // Uso de like para búsqueda parcial (insensible a mayúsculas/minúsculas)
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("email")), "%" + email.toLowerCase() + "%");
        };
    }

    public static Specification<ClienteEntity> hasNombre(String nombre) {
        return (root, query, criteriaBuilder) -> {
            if (nombre == null || nombre.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
        };
    }

    public static Specification<ClienteEntity> hasTelefono(String telefono) {
        return (root, query, criteriaBuilder) -> {
            if (telefono == null || telefono.trim().isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("telefono")), "%" + telefono.toLowerCase() + "%");
        };
    }

    public static Specification<ClienteEntity> filter (ClienteFilterDto filter) {
        List<Specification<ClienteEntity>> specs = new ArrayList<>();

        // Solo se agrega la especificación si el campo tiene valor
        if (filter.getEmail() != null && !filter.getEmail().trim().isEmpty()) {
            specs.add(hasEmail(filter.getEmail()));
        }
        if (filter.getNombre() != null && !filter.getNombre().trim().isEmpty()) {
            specs.add(hasNombre(filter.getNombre()));
        }
        if (filter.getTelefono() != null && !filter.getTelefono().trim().isEmpty()) {
            specs.add(hasTelefono(filter.getTelefono()));
        }

        // Combinar todas las especificaciones con AND, o devolver un predicado "true" si no hay filtro.
        Specification<ClienteEntity> result = specs.stream()
                .reduce(Specification::and)
                .orElse((root, query, criteriaBuilder) -> criteriaBuilder.conjunction());
        return result;
    }
}
