package com.tlalocalli.gym.web.controller;

import com.tlalocalli.gym.service.AccesoService;
import com.tlalocalli.gym.persistence.dto.request.AccesoRequest;
import com.tlalocalli.gym.persistence.dto.response.AccesoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accesos")
@RequiredArgsConstructor
public class AccesoController {

    private final AccesoService accesoService;

    @PostMapping
    public ResponseEntity<AccesoResponse> createAcceso(
            @Valid @RequestBody AccesoRequest request) {
        return ResponseEntity.ok(accesoService.createAcceso(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AccesoResponse> updateAcceso(
            @PathVariable("id") Integer id,
            @Valid @RequestBody AccesoRequest request) {
        return ResponseEntity.ok(accesoService.updateAcceso(id, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccesoResponse> getAccesoById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(accesoService.getAccesoById(id));
    }

    @GetMapping
    public ResponseEntity<Page<AccesoResponse>> getAllAccesos(Pageable pageable) {
        return ResponseEntity.ok(accesoService.getAllAccesos(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcceso(@PathVariable("id") Integer id) {
        accesoService.deleteAcceso(id);
        return ResponseEntity.noContent().build();
    }
}
