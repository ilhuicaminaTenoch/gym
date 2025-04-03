package com.tlalocalli.gym.service;

import com.tlalocalli.gym.persistence.entity.UsuarioEntity;
import com.tlalocalli.gym.persistence.repository.UsuarioRepository;
import com.tlalocalli.gym.persistence.dto.request.UsuarioRequest;
import com.tlalocalli.gym.persistence.dto.response.UsuarioResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioResponse createUsuario(UsuarioRequest request) {
        // Validar si ya existe usuario por username o email (opcional)
        if (usuarioRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("El username ya está registrado");
        }
        if (usuarioRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("El email ya está registrado");
        }

        // Crear entidad y asignar valores
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setUsername(request.getUsername());
        usuario.setEmail(request.getEmail());
        // Hashear la contraseña con BCrypt
        String hashed = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        usuario.setPasswordHash(hashed);
        // Los valores por defecto para role y estado se establecen en la entidad (por ejemplo, CLIENTE y ACTIVO)

        UsuarioEntity saved = usuarioRepository.save(usuario);
        log.info("Usuario creado: {}", saved.getUsername());
        return mapToResponse(saved);
    }

    private UsuarioResponse mapToResponse(UsuarioEntity usuario) {
        return UsuarioResponse.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .role(usuario.getRole().name())
                .estado(usuario.getEstado().name())
                .build();
    }

}
