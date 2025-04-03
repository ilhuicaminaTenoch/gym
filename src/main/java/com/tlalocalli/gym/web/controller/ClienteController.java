package com.tlalocalli.gym.web.controller;

import com.tlalocalli.gym.persistence.dto.ClienteFilterDto;
import com.tlalocalli.gym.persistence.entity.ClienteEntity;
import com.tlalocalli.gym.service.ClienteService;
import com.tlalocalli.gym.persistence.dto.request.ClienteRequest;
import com.tlalocalli.gym.persistence.dto.request.ClienteUpdateRequest;
import com.tlalocalli.gym.persistence.dto.response.ClienteResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Slf4j
public class ClienteController {
    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteResponse> createCliente(@Valid @RequestBody ClienteRequest request) {
        ClienteEntity cliente = mapRequestToCliente(request);
        ClienteEntity savedCliente = clienteService.createCliente(cliente);
        return new ResponseEntity<>(mapClienteToResponse(savedCliente), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<ClienteResponse>> getCliente(
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "telefono", required = false) String telefono,
            @PageableDefault(page = 0, size = 10, sort = "nombre", direction = Sort.Direction.ASC) Pageable pageable) {
        ClienteFilterDto filterDto = new ClienteFilterDto(nombre, email, telefono);
        return ResponseEntity.ok(clienteService.getClientes(filterDto, pageable));
    }

    @PutMapping()
    public ResponseEntity<ClienteResponse> updateCliente(
            @Valid @RequestBody ClienteUpdateRequest request) {
        ClienteResponse update = clienteService.updateCliente(request);
        return ResponseEntity.ok(update);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable("id") Integer id) {
        clienteService.deleteCliente(id);
        return ResponseEntity.noContent().build();
    }

    /* @GetMapping
    public ResponseEntity<Page<ClienteResponse>> getAllClientes(Pageable pageable) {
        Page<ClienteEntity> clientePage = clienteService.getAllClientes(pageable);
        Page<ClienteResponse> responsePage = clientePage.map(this::mapClienteToResponse);
        return ResponseEntity.ok(responsePage);
    } */

    // Método auxiliar para mapear de ClienteRequest a Cliente (entidad)
    private ClienteEntity mapRequestToCliente(ClienteRequest request) {
        ClienteEntity cliente = new ClienteEntity();
        cliente.setNombre(request.getNombre());
        cliente.setApellido(request.getApellido());
        cliente.setFechaNacimiento(request.getFechaNacimiento());
        cliente.setEmail(request.getEmail());
        cliente.setTelefono(request.getTelefono());
        cliente.setDireccion(request.getDireccion());
        return cliente;
    }

    // Método auxiliar para mapear de Cliente (entidad) a ClienteResponse
    private ClienteResponse mapClienteToResponse(ClienteEntity cliente) {
        return ClienteResponse.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .fechaNacimiento(cliente.getFechaNacimiento())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .build();
    }
}
