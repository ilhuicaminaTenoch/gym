package com.tlalocalli.gym.web.controller;

import com.tlalocalli.gym.service.UsuarioDetalleService;
import com.tlalocalli.gym.persistence.dto.request.UsuarioDetalleRequest;
import com.tlalocalli.gym.persistence.dto.response.UsuarioDetalleResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/usuario-detalle")
@RequiredArgsConstructor
public class UsuarioDetalleController {

    private final UsuarioDetalleService usuarioDetalleService;

    /**
     * Endpoint para crear o actualizar el detalle de un usuario.
     * Se utiliza el ID del usuario (idUsuario) para identificar a qué usuario asignar o actualizar el detalle.
     */
    @PostMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDetalleResponse> saveOrUpdateDetalle(
            @PathVariable("idUsuario") Integer idUsuario,
            @Valid @RequestBody UsuarioDetalleRequest request) {
        UsuarioDetalleResponse response = usuarioDetalleService.saveOrUpdateUsuarioDetalle(idUsuario, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para obtener el detalle de un usuario según su ID.
     */
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioDetalleResponse> getDetalleByUsuario(
            @PathVariable("idUsuario") Integer idUsuario) {
        UsuarioDetalleResponse response = usuarioDetalleService.getUsuarioDetalleByUsuarioId(idUsuario);
        return ResponseEntity.ok(response);
    }

    /**
     * Endpoint para eliminar el detalle de un usuario, usando el ID del detalle.
     */
    @DeleteMapping("/{idDetalle}")
    public ResponseEntity<Void> deleteDetalle(@PathVariable("idDetalle") Integer idDetalle) {
        usuarioDetalleService.deleteUsuarioDetalle(idDetalle);
        return ResponseEntity.noContent().build();
    }
}
