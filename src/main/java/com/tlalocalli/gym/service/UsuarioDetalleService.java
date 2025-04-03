package com.tlalocalli.gym.service;

import com.tlalocalli.gym.persistence.entity.UsuarioDetalleEntity;
import com.tlalocalli.gym.persistence.entity.UsuarioEntity;
import com.tlalocalli.gym.persistence.repository.UsuarioDetalleRepository;
import com.tlalocalli.gym.persistence.repository.UsuarioRepository;
import com.tlalocalli.gym.persistence.dto.request.UsuarioDetalleRequest;
import com.tlalocalli.gym.persistence.dto.response.UsuarioDetalleResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioDetalleService {

    private final UsuarioDetalleRepository usuarioDetalleRepository;
    private final UsuarioRepository usuarioRepository;

    /**
     * Crea o actualiza el detalle de un usuario.
     * Si ya existe el detalle para el usuario, se actualiza; de lo contrario, se crea uno nuevo.
     *
     * @param idUsuario El ID del usuario al que se le asigna el detalle.
     * @param request   Objeto con los datos para el detalle.
     * @return UsuarioDetalleResponse con la información guardada.
     */
    public UsuarioDetalleResponse saveOrUpdateUsuarioDetalle(Integer idUsuario, UsuarioDetalleRequest request) {
        // Buscar el usuario en la base de datos
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));

        // Buscar si ya existe un detalle para este usuario
        Optional<UsuarioDetalleEntity> optionalDetalle = usuarioDetalleRepository.findByUsuario(usuario);
        UsuarioDetalleEntity detalle;
        if (optionalDetalle.isPresent()) {
            detalle = optionalDetalle.get();
            log.info("Actualizando detalle para el usuario con id: {}", idUsuario);
        } else {
            detalle = new UsuarioDetalleEntity();
            detalle.setUsuario(usuario);
            log.info("Creando nuevo detalle para el usuario con id: {}", idUsuario);
        }

        // Asignar los valores del request al detalle
        detalle.setNombreCompleto(request.getNombreCompleto());
        detalle.setDireccion(request.getDireccion());
        detalle.setTelefono(request.getTelefono());
        detalle.setFechaNacimiento(request.getFechaNacimiento());
        detalle.setGenero(request.getGenero()); // Se asume que request.getGenero() retorna el enum Genero

        // Guardar el detalle
        UsuarioDetalleEntity saved = usuarioDetalleRepository.save(detalle);
        return mapToResponse(saved);
    }

    /**
     * Obtiene el detalle del usuario a partir del ID del usuario.
     *
     * @param idUsuario El ID del usuario.
     * @return UsuarioDetalleResponse con la información del detalle.
     */
    public UsuarioDetalleResponse getUsuarioDetalleByUsuarioId(Integer idUsuario) {
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + idUsuario));

        UsuarioDetalleEntity detalle = usuarioDetalleRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RuntimeException("Detalle de usuario no encontrado para el usuario con id: " + idUsuario));

        return mapToResponse(detalle);
    }

    /**
     * Elimina el detalle de usuario dado su ID.
     *
     * @param idDetalle El ID del detalle a eliminar.
     */
    public void deleteUsuarioDetalle(Integer idDetalle) {
        usuarioDetalleRepository.deleteById(idDetalle);
    }

    // Método auxiliar para mapear la entidad a DTO de respuesta
    private UsuarioDetalleResponse mapToResponse(UsuarioDetalleEntity entity) {
        return UsuarioDetalleResponse.builder()
                .id(entity.getId())
                .nombreCompleto(entity.getNombreCompleto())
                .direccion(entity.getDireccion())
                .telefono(entity.getTelefono())
                .fechaNacimiento(entity.getFechaNacimiento())
                .genero(entity.getGenero().name())
                .build();
    }
}
