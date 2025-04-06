package com.tlalocalli.gym.web.controller;

import com.tlalocalli.gym.persistence.dto.request.SuscripcionRequest;
import com.tlalocalli.gym.persistence.dto.response.SuscripcionResponse;
import com.tlalocalli.gym.service.SuscripcionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/suscripciones")
@RequiredArgsConstructor
public class SuscripcionController {

    private final SuscripcionService suscripcionService;

    @GetMapping
    public ResponseEntity<List<SuscripcionResponse>> listarSuscripciones() {
        List<SuscripcionResponse> lista = suscripcionService.obtenerTodasSuscripciones();
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuscripcionResponse> obtenerSuscripcion(@PathVariable Integer id) {
        SuscripcionResponse respuesta = suscripcionService.obtenerSuscripcionPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    public ResponseEntity<SuscripcionResponse> crearSuscripcion(@Valid @RequestBody SuscripcionRequest request) {
        SuscripcionResponse respuesta = suscripcionService.crearSuscripcion(request);
        return ResponseEntity.ok(respuesta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuscripcionResponse> actualizarSuscripcion(@PathVariable Integer id,
                                                                     @Valid @RequestBody SuscripcionRequest request) {
        SuscripcionResponse respuesta = suscripcionService.actualizarSuscripcion(id, request);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarSuscripcion(@PathVariable Integer id) {
        suscripcionService.eliminarSuscripcion(id);
        return ResponseEntity.noContent().build();
    }
}
