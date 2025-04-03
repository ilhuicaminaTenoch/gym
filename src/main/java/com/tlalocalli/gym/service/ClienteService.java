package com.tlalocalli.gym.service;

import com.tlalocalli.gym.persistence.dto.ClienteFilterDto;
import com.tlalocalli.gym.persistence.entity.ClienteEntity;
import com.tlalocalli.gym.persistence.repository.ClienteRepository;
import com.tlalocalli.gym.persistence.dto.request.ClienteUpdateRequest;
import com.tlalocalli.gym.persistence.dto.response.ClienteResponse;
import com.tlalocalli.gym.util.ClienteSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {
    private final ClienteRepository clienteRepository;

    // Crear un nuevo Cliente
    public ClienteEntity createCliente(ClienteEntity cliente) {
        log.info("Creando cliente: {}", cliente);
        return clienteRepository.save(cliente);
    }

    // Actualizar un Cliente existente
    public ClienteResponse updateCliente(ClienteUpdateRequest request) {
        ClienteEntity existingCliente = clienteRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + request.getId()));

        // 2. Actualizar solo los campos que vengan con valor
        if (request.getNombre() != null) {
            existingCliente.setNombre(request.getNombre());
        }
        if (request.getApellido() != null) {
            existingCliente.setApellido(request.getApellido());
        }
        if (request.getFechaNacimiento() != null) {
            existingCliente.setFechaNacimiento(request.getFechaNacimiento());
        }
        if (request.getEmail() != null) {
            existingCliente.setEmail(request.getEmail());
        }
        if (request.getTelefono() != null) {
            existingCliente.setTelefono(request.getTelefono());
        }
        if (request.getDireccion() != null) {
            existingCliente.setDireccion(request.getDireccion());
        }

        // 3. Guardar cambios en la base de datos
        existingCliente = clienteRepository.save(existingCliente);

        // 4. Convertir la entidad a DTO de respuesta y retornarlo
        log.info("Actualizando cliente: {}", existingCliente);
        return mapClienteToResponse(existingCliente);

    }

    // Obtener un Cliente por su id
    public ClienteEntity getClientByNombre(Integer id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
    }

    public Page<ClienteResponse> getClientes(ClienteFilterDto filter, Pageable pageable) {
        Specification<ClienteEntity> specification = ClienteSpecification.filter(filter);

        Page<ClienteEntity> clienteEntities = clienteRepository.findAll(specification, pageable);

        List<ClienteResponse> dtoToList = mapEntitiesToDto(clienteEntities.getContent());

        return new PageImpl<>(dtoToList, pageable, clienteEntities.getTotalElements());

    }

    // Eliminar un Cliente por su id
    public void deleteCliente(Integer id) {
        ClienteEntity existingCliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        log.info("Eliminando cliente: {}", existingCliente);
        clienteRepository.delete(existingCliente);
    }

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

    private List<ClienteResponse> mapEntitiesToDto(List<ClienteEntity> entities) {
        return entities.stream()
                .map(this::mapClienteToResponse)
                .collect(Collectors.toList());
    }
}
