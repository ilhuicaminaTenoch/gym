package com.tlalocalli.gym.web.controller;

import com.tlalocalli.gym.persistence.dto.request.VentaRequest;
import com.tlalocalli.gym.persistence.dto.response.VentaResponse;
import com.tlalocalli.gym.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponse> crearVenta(@Valid @RequestBody VentaRequest request) {
        VentaResponse response = ventaService.crearVenta(request);
        return ResponseEntity.ok(response);
    }
}
