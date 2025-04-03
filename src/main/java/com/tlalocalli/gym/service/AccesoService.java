package com.tlalocalli.gym.service;

import com.tlalocalli.gym.persistence.entity.AccesoEntity;
import com.tlalocalli.gym.persistence.entity.ClienteEntity;
import com.tlalocalli.gym.persistence.repository.AccesoRepository;
import com.tlalocalli.gym.persistence.repository.ClienteRepository;
import com.tlalocalli.gym.persistence.dto.request.AccesoRequest;
import com.tlalocalli.gym.persistence.dto.response.AccesoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccesoService {

    private final AccesoRepository accesoRepository;
    private final ClienteRepository clienteRepository;

    public AccesoResponse createAcceso(AccesoRequest request) {
        log.info("Creando un nuevo acceso: {}", request);

        // Verificar que el cliente existe
        ClienteEntity cliente = clienteRepository.findById(request.getIdCliente())
                .orElseThrow(() -> new RuntimeException(
                        "Cliente no encontrado con id: " + request.getIdCliente()));

        AccesoEntity entity = new AccesoEntity();
        entity.setCliente(cliente);
        entity.setFechaAcceso(request.getFechaAcceso());
        entity.setFechaSalida(request.getFechaSalida());
        entity.setMetodoAcceso(request.getMetodoAcceso());

        // Guardar
        entity = accesoRepository.save(entity);

        return mapToResponse(entity);
    }

    public AccesoResponse updateAcceso(Integer id, AccesoRequest request) {
        log.info("Actualizando acceso con id: {}", id);

        AccesoEntity existing = accesoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Acceso no encontrado con id: " + id));

        // Verificar si hay cambio de cliente (opcional)
        if (!existing.getCliente().getId().equals(request.getIdCliente())) {
            ClienteEntity cliente = clienteRepository.findById(request.getIdCliente())
                    .orElseThrow(() -> new RuntimeException(
                            "Cliente no encontrado con id: " + request.getIdCliente()));
            existing.setCliente(cliente);
        }

        existing.setFechaAcceso(request.getFechaAcceso());
        existing.setFechaSalida(request.getFechaSalida());
        existing.setMetodoAcceso(request.getMetodoAcceso());

        // Guardar cambios
        existing = accesoRepository.save(existing);

        return mapToResponse(existing);
    }

    public AccesoResponse getAccesoById(Integer id) {
        AccesoEntity entity = accesoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Acceso no encontrado con id: " + id));
        return mapToResponse(entity);
    }

    public Page<AccesoResponse> getAllAccesos(Pageable pageable) {
        return accesoRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    public void deleteAcceso(Integer id) {
        AccesoEntity existing = accesoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        "Acceso no encontrado con id: " + id));
        accesoRepository.delete(existing);
    }

    private AccesoResponse mapToResponse(AccesoEntity entity) {
        return AccesoResponse.builder()
                .id(entity.getId())
                .idCliente(entity.getCliente().getId())
                .fechaAcceso(entity.getFechaAcceso())
                .fechaSalida(entity.getFechaSalida())
                .metodoAcceso(entity.getMetodoAcceso())
                .build();
    }
}
