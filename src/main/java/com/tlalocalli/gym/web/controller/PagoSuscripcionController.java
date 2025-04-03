package com.tlalocalli.gym.web.controller;

import com.tlalocalli.gym.persistence.dto.request.PagoSuscripcionRequest;
import com.tlalocalli.gym.persistence.dto.response.PagoSuscripcionResponse;
import com.tlalocalli.gym.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pagos/suscripcion")
@RequiredArgsConstructor
public class PagoSuscripcionController {

    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<PagoSuscripcionResponse> registrarPagoSuscripcion(@RequestBody PagoSuscripcionRequest request) {
        PagoSuscripcionResponse response = pagoService.registrarPagoSuscripcion(request);
        return ResponseEntity.ok(response);
    }
}
