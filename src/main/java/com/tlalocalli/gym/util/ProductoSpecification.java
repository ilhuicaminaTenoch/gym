package com.tlalocalli.gym.util;

import com.tlalocalli.gym.persistence.dto.ProductoFilterDto;
import com.tlalocalli.gym.persistence.entity.ProductoEntity;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class ProductoSpecification {

    private ProductoSpecification() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static Specification<ProductoEntity> hasNombre(String nombre) {
        return (Root<ProductoEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (nombre == null || nombre.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%");
        };
    }

    public static Specification<ProductoEntity> hasCodigoBarras(String codigoBarras) {
        return (Root<ProductoEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (codigoBarras == null || codigoBarras.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("codigoBarras")), "%" + codigoBarras.toLowerCase() + "%");
        };
    }

    public static Specification<ProductoEntity> filter(ProductoFilterDto filter) {
        List<Specification<ProductoEntity>> specs = new ArrayList<>();

        if (filter.getNombre() != null && !filter.getNombre().trim().isEmpty()) {
            specs.add(hasNombre(filter.getNombre()));
        }
        if (filter.getCodigoBarras() != null && !filter.getCodigoBarras().trim().isEmpty()) {
            specs.add(hasCodigoBarras(filter.getCodigoBarras()));
        }

        return specs.stream()
                .reduce(Specification::and)
                .orElse((root, query, cb) -> cb.conjunction());
    }
}
