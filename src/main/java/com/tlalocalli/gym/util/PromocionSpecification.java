package com.tlalocalli.gym.util;

import com.tlalocalli.gym.persistence.dto.PromocionFilterDto;
import com.tlalocalli.gym.persistence.entity.PromocionEntity;
import com.tlalocalli.gym.persistence.enums.TipoPlan;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class PromocionSpecification {

    private PromocionSpecification() {
        throw new UnsupportedOperationException("Utility class");
    }

    // Filtro por descripción (LIKE insensible a mayúsculas)
    public static Specification<PromocionEntity> hasDescripcion(String descripcion) {
        return (Root<PromocionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (descripcion == null || descripcion.trim().isEmpty()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("descripcion")), "%" + descripcion.toLowerCase() + "%");
        };
    }

    // Filtro por descuento (igualdad)
    public static Specification<PromocionEntity> hasDescuento(java.math.BigDecimal descuento) {
        return (Root<PromocionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (descuento == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("descuento"), descuento);
        };
    }

    // Filtro por tipoPlan (igualdad; se convierte a mayúsculas para que coincida con el enum)
    public static Specification<PromocionEntity> hasTipoPlan(TipoPlan tipoPlan) {
        return (Root<PromocionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (tipoPlan == null) {
                return cb.conjunction();
            }
            return cb.equal(root.get("tipoPlan"), tipoPlan);
        };
    }

    // Filtro para que la vigenciaInicio sea mayor o igual que el valor proporcionado
    public static Specification<PromocionEntity> hasVigenciaInicioGte(LocalDateTime vigenciaInicio) {
        return (Root<PromocionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (vigenciaInicio == null) {
                return cb.conjunction();
            }
            return cb.greaterThanOrEqualTo(root.get("vigenciaInicio"), vigenciaInicio);
        };
    }

    // Filtro para que la vigenciaFin sea menor o igual que el valor proporcionado
    public static Specification<PromocionEntity> hasVigenciaFinLte(LocalDateTime vigenciaFin) {
        return (Root<PromocionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (vigenciaFin == null) {
                return cb.conjunction();
            }
            return cb.lessThanOrEqualTo(root.get("vigenciaFin"), vigenciaFin);
        };
    }

    // Filtro para promociones cuyo rango de vigencia (inicio y fin) esté entre dos fechas dadas
    public static Specification<PromocionEntity> hasVigenciaBetween(LocalDateTime start, LocalDateTime end) {
        return (Root<PromocionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (start == null || end == null) {
                return cb.conjunction();
            }
            Predicate inicioBetween = cb.between(root.get("vigenciaInicio"), start, end);
            Predicate finBetween = cb.between(root.get("vigenciaFin"), start, end);
            return cb.and(inicioBetween, finBetween);
        };
    }

    // Filtro para promociones activas en una fecha dada (la fecha se encuentra entre vigenciaInicio y vigenciaFin)
    public static Specification<PromocionEntity> isActiveOn(LocalDateTime date) {
        return (Root<PromocionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (date == null) {
                return cb.conjunction();
            }
            Predicate inicioLeDate = cb.lessThanOrEqualTo(root.get("vigenciaInicio"), date);
            Predicate finGeDate = cb.greaterThanOrEqualTo(root.get("vigenciaFin"), date);
            return cb.and(inicioLeDate, finGeDate);
        };
    }

    // Filtro para promociones que ya hayan vencido: vigenciaFin es menor que la fecha actual
    public static Specification<PromocionEntity> isExpired(Boolean vencidas) {
        return (Root<PromocionEntity> root, CriteriaQuery<?> query, CriteriaBuilder cb) -> {
            if (vencidas == null || !vencidas) {
                return cb.conjunction();
            }
            return cb.lessThan(root.get("vigenciaFin"), LocalDateTime.now());
        };
    }

    // Método filter que combina todos los filtros
    public static Specification<PromocionEntity> filter(PromocionFilterDto filter) {
        Specification<PromocionEntity> spec = Specification.where(hasDescripcion(filter.getDescripcion()))
                .and(hasDescuento(filter.getDescuento()))
                .and(hasTipoPlan(filter.getTipoPlan()))
                .and(hasVigenciaInicioGte(filter.getVigenciaInicio()))
                .and(hasVigenciaFinLte(filter.getVigenciaFin()));

        if (filter.getFechaDesde() != null && filter.getFechaHasta() != null) {
            spec = spec.and(hasVigenciaBetween(filter.getFechaDesde(), filter.getFechaHasta()));
        }

        spec = spec.and(isExpired(filter.getVencidas()));

        return spec;
    }

}
