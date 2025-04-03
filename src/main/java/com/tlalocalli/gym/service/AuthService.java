package com.tlalocalli.gym.service;

import com.tlalocalli.gym.persistence.entity.LogAccesoEntity;
import com.tlalocalli.gym.persistence.entity.UsuarioDetalleEntity;
import com.tlalocalli.gym.persistence.entity.UsuarioEntity;
import com.tlalocalli.gym.persistence.entity.UsuarioTokenEntity;
import com.tlalocalli.gym.persistence.enums.TokenType;
import com.tlalocalli.gym.persistence.repository.LogAccesoRepository;
import com.tlalocalli.gym.persistence.repository.UsuarioDetalleRepository;
import com.tlalocalli.gym.persistence.repository.UsuarioRepository;
import com.tlalocalli.gym.persistence.repository.UsuarioTokenRepository;
import com.tlalocalli.gym.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final LogAccesoRepository logAccesoRepository;
    private final UsuarioTokenRepository usuarioTokenRepository;
    private final UsuarioDetalleRepository usuarioDetalleRepository;
    private final JwtUtil jwtUtil;

    public String login(String usernameOrEmail, String password, String ipAddress) {
        // Buscar usuario por username o email
        Optional<UsuarioEntity> optionalUsuario = usuarioRepository.findByUsername(usernameOrEmail);
        if (!optionalUsuario.isPresent()) {
            optionalUsuario = usuarioRepository.findByEmail(usernameOrEmail);
        }
        if (!optionalUsuario.isPresent()) {
            logAcceso(null, ipAddress, false);
            throw new RuntimeException("Credenciales inválidas");
        }

        UsuarioEntity usuario = optionalUsuario.get();

        // Validar contraseña (asumiendo que se almacena con BCrypt)
        if (!BCrypt.checkpw(password, usuario.getPasswordHash())) {
            logAcceso(usuario, ipAddress, false);
            throw new RuntimeException("Credenciales inválidas");
        }

        // Registrar log de acceso exitoso
        logAcceso(usuario, ipAddress, true);

        String fullName = usuario.getUsername();
        Optional<UsuarioDetalleEntity> optionalDetalle = usuarioDetalleRepository.findByUsuario(usuario);
        if (optionalDetalle.isPresent() && optionalDetalle.get().getNombreCompleto() != null) {
            fullName = optionalDetalle.get().getNombreCompleto();
        }

        // Generar token JWT
        String token = jwtUtil.generateToken(
                usuario.getUsername(),
                usuario.getId(),
                fullName,
                usuario.getRole(),
                usuario.getEmail()
        );

        // Guardar el token en la tabla USUARIO_TOKEN
        UsuarioTokenEntity usuarioToken = new UsuarioTokenEntity();
        usuarioToken.setUsuario(usuario);
        usuarioToken.setToken(token);
        usuarioToken.setTokenType(TokenType.ACCESS); // o REFRESH según convenga
        usuarioToken.setExpiresAt(LocalDateTime.now().plusSeconds(3600)); // Expiración de 1 hora
        usuarioToken.setRevoked(false);
        usuarioTokenRepository.save(usuarioToken);

        return token;
    }

    private void logAcceso(UsuarioEntity usuario, String ipAddress, boolean success) {
        LogAccesoEntity logAcceso = new LogAccesoEntity();
        logAcceso.setUsuario(usuario);
        logAcceso.setIpAddress(ipAddress);
        logAcceso.setSuccess(success);
        logAcceso.setLoginTime(LocalDateTime.now());
        logAccesoRepository.save(logAcceso);
    }
}
