package com.tlalocalli.gym.web.controller;

import com.tlalocalli.gym.service.UsuarioService;
import com.tlalocalli.gym.persistence.dto.request.UsuarioRequest;
import com.tlalocalli.gym.persistence.dto.response.UsuarioResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponse> createUsuario(@Valid @RequestBody UsuarioRequest request) {
        UsuarioResponse response = usuarioService.createUsuario(request);
        return ResponseEntity.ok(response);
    }
}
