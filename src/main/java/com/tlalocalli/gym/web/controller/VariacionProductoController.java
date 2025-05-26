package com.tlalocalli.gym.web.controller;

import com.tlalocalli.gym.persistence.dto.request.AjusteStockRequest;
import com.tlalocalli.gym.persistence.dto.request.VariacionProductoRequest;
import com.tlalocalli.gym.persistence.dto.response.VariacionProductoResponse;
import com.tlalocalli.gym.persistence.entity.VariacionProductoEntity;
import com.tlalocalli.gym.service.VariacionProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/productos/{productoId}/variaciones")
@RequiredArgsConstructor
public class VariacionProductoController {

    private final VariacionProductoService service;

    @PostMapping
    public ResponseEntity<VariacionProductoResponse> crear(
            @PathVariable Integer productoId,
            @Valid @RequestBody VariacionProductoRequest req) {
        VariacionProductoEntity v = service.crearVariacion(productoId, req);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToDto(v));
    }

    @GetMapping
    public List<VariacionProductoResponse> listar(@PathVariable Integer productoId) {
        return service.listarVariaciones(productoId)
                .stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VariacionProductoResponse> obtener(
            @PathVariable Integer productoId,
            @PathVariable Integer id) {
        VariacionProductoEntity v = service.obtenerVariacion(id);
        return ResponseEntity.ok(mapToDto(v));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VariacionProductoResponse> actualizar(
            @PathVariable Integer productoId,
            @PathVariable Integer id,
            @Valid @RequestBody VariacionProductoRequest req) {
        VariacionProductoEntity v = service.actualizarVariacion(id, req);
        return ResponseEntity.ok(mapToDto(v));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer productoId,
            @PathVariable Integer id) {
        service.eliminarVariacion(id);
        return ResponseEntity.noContent().build();
    }

    private VariacionProductoResponse mapToDto(VariacionProductoEntity e) {
        VariacionProductoResponse dto = new VariacionProductoResponse();
        dto.setId(e.getId());
        dto.setSku(e.getSku());
        dto.setPrecio(e.getPrecio());
        dto.setStock(e.getStock());
        dto.setImagenUrl(e.getImagenUrl());
        try {
            dto.setAtributos(
                    new com.fasterxml.jackson.databind.ObjectMapper()
                            .readValue(e.getAtributosJson(), Map.class)
            );
        } catch (Exception ex) {
            dto.setAtributos(Map.of());
        }
        return dto;
    }

    @PutMapping("/ajustar")
    public ResponseEntity<Void> ajustarStock(@Valid @RequestBody AjusteStockRequest solicitud) {
        service.ajustarStock(solicitud);
        return ResponseEntity.noContent().build();
    }
}
