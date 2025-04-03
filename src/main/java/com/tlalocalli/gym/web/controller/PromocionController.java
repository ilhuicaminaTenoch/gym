package com.tlalocalli.gym.web.controller;

import com.tlalocalli.gym.persistence.dto.PromocionFilterDto;
import com.tlalocalli.gym.persistence.enums.TipoPlan;
import com.tlalocalli.gym.service.PromocionService;
import com.tlalocalli.gym.persistence.dto.request.PromocionRequest;
import com.tlalocalli.gym.persistence.dto.response.PromocionResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/promociones")
@RequiredArgsConstructor
@Slf4j
public class PromocionController {

    private final PromocionService promocionService;

    @PostMapping
    public ResponseEntity<PromocionResponse> createPromocion(
            @Valid @RequestBody PromocionRequest request) {
        PromocionResponse response = promocionService.createPromocion(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromocionResponse> updatePromocion(
            @PathVariable("id") Integer id,
            @Valid @RequestBody PromocionRequest request) {
        PromocionResponse response = promocionService.updatePromocion(id, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<PromocionResponse>> searchPromociones(
            @RequestParam(value = "descripcion", required = false) String descripcion,
            @RequestParam(value = "descuento", required = false) BigDecimal descuento,
            @RequestParam(value = "tipoPlan", required = false) TipoPlan tipoPlan,
            @RequestParam(value = "vigenciaInicio", required = false) String vigenciaInicioStr,
            @RequestParam(value = "vigenciaFin", required = false) String vigenciaFinStr,
            @RequestParam(value = "fechaDesde", required = false) String fechaDesdeStr,
            @RequestParam(value = "fechaHasta", required = false) String fechaHastaStr,
            @RequestParam(value = "vencidas", required = false) Boolean vencidas,
            Pageable pageable) {

        // Convertir los strings a LocalDateTime si se proporcionan
        LocalDateTime vigenciaInicio = vigenciaInicioStr != null ? LocalDateTime.parse(vigenciaInicioStr) : null;
        LocalDateTime vigenciaFin = vigenciaFinStr != null ? LocalDateTime.parse(vigenciaFinStr) : null;
        LocalDateTime fechaDesde = fechaDesdeStr != null ? LocalDateTime.parse(fechaDesdeStr) : null;
        LocalDateTime fechaHasta = fechaHastaStr != null ? LocalDateTime.parse(fechaHastaStr) : null;

        // Construir el DTO de filtro
        PromocionFilterDto filter = new PromocionFilterDto();
        filter.setDescripcion(descripcion);
        filter.setDescuento(descuento);
        filter.setTipoPlan(tipoPlan);
        filter.setVigenciaInicio(vigenciaInicio);
        filter.setVigenciaFin(vigenciaFin);
        filter.setFechaDesde(fechaDesde);
        filter.setFechaHasta(fechaHasta);
        filter.setVencidas(vencidas);

        Page<PromocionResponse> result = promocionService.getPromociones(filter, pageable);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromocion(@PathVariable("id") Integer id) {
        promocionService.deletePromocion(id);
        return ResponseEntity.noContent().build();
    }
}
