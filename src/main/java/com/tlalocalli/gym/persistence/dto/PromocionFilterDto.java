package com.tlalocalli.gym.persistence.dto;

import com.tlalocalli.gym.persistence.enums.TipoPlan;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromocionFilterDto {
    private String descripcion;
    private BigDecimal descuento;
    private TipoPlan tipoPlan;
    // Filtros individuales para vigencia (opcional)
    private LocalDateTime vigenciaInicio;
    private LocalDateTime vigenciaFin;
    // Filtro para rango completo: promociones cuya vigencia (inicio y fin) estén entre dos fechas
    private LocalDateTime fechaDesde;
    private LocalDateTime fechaHasta;
    // Si es true, se filtran solo las promociones que ya han vencido (vigenciaFin < now)
    private Boolean vencidas;
}
