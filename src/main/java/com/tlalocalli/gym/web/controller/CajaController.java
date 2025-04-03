package com.tlalocalli.gym.web.controller;

import com.tlalocalli.gym.persistence.dto.request.CajaRequest;
import com.tlalocalli.gym.persistence.dto.response.CajaResponse;
import com.tlalocalli.gym.service.CajaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cajas")
@RequiredArgsConstructor
public class CajaController {

    private final CajaService cajaService;

    @PostMapping
    public ResponseEntity<CajaResponse> createCaja(@RequestBody CajaRequest request) {
        CajaResponse response = cajaService.createCaja(request);
        return ResponseEntity.ok(response);
    }
}
