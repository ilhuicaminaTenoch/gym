package com.tlalocalli.gym.web.controller;

import com.tlalocalli.gym.service.AuthService;
import com.tlalocalli.gym.persistence.dto.request.LoginRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/login")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request,
                                   @RequestHeader(value = "X-Forwarded-For", required = false) String ipAddress) {
        // Si no se envía IP, se puede obtener de request.getRemoteAddr() en un filtro
        String token = authService.login(request.getUsernameOrEmail(), request.getPassword(), ipAddress);
        return ResponseEntity.ok().body("{\"token\": \"" + token + "\"}");
    }
}
