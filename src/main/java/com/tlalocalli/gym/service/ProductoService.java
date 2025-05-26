package com.tlalocalli.gym.service;

import com.tlalocalli.gym.persistence.dto.ProductoFilterDto;
import com.tlalocalli.gym.persistence.dto.request.AjusteStockRequest;
import com.tlalocalli.gym.persistence.dto.request.ItemAjusteStockRequest;
import com.tlalocalli.gym.persistence.entity.ProductoEntity;
import com.tlalocalli.gym.persistence.repository.ProductoRepository;
import com.tlalocalli.gym.persistence.dto.request.VariacionProductoRequest;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlalocalli.gym.persistence.dto.request.ProductoRequest;
import com.tlalocalli.gym.persistence.dto.request.ProductoUpdateRequest;
import com.tlalocalli.gym.persistence.dto.response.VariacionProductoResponse;
import com.tlalocalli.gym.persistence.entity.VariacionProductoEntity;
import com.tlalocalli.gym.persistence.dto.response.ProductoResponse;
import com.tlalocalli.gym.util.FileUtils;
import com.tlalocalli.gym.util.ProductoSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoService {

    private final ProductoRepository productoRepository;
    private final FileUtils fileUtils;

    @Transactional // Asegurar atomicidad
    public ProductoResponse createProducto(ProductoRequest request, MultipartFile imagenFile) {
        log.info("Creando nuevo producto con variaciones: {}", request); 

        // Deserialización de variacionesJson
        ObjectMapper objectMapper = new ObjectMapper();
        List<VariacionProductoRequest> variacionesDeserializadas = new ArrayList<>();
        if (request.getVariacionesJson() != null && !request.getVariacionesJson().isEmpty()) {
            try {
                variacionesDeserializadas = objectMapper.readValue(request.getVariacionesJson(), new TypeReference<List<VariacionProductoRequest>>() {});
                request.setVariaciones(variacionesDeserializadas); // Sobrescribir la lista en ProductoRequest
            } catch (Exception e) {
                log.error("Error al deserializar variacionesJson: {}", request.getVariacionesJson(), e);
                // Considerar lanzar una excepción aquí o manejar el error apropiadamente,
                // por ejemplo, devolver un ResponseEntity con error o lanzar una excepción custom.
                // Por ahora, solo logueamos y continuamos con una lista vacía de variaciones si falla.
                // Esto podría mejorarse con un manejo de errores más robusto.
            }
        }

        ProductoEntity entity = new ProductoEntity();
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setPrecio(request.getPrecio());
        entity.setStock(request.getStock());
        entity.setCodigoBarras(request.getCodigoBarras());
        entity.setSku(request.getSku());
        entity.setEstatus(request.getEstatus());

        if (imagenFile != null && !imagenFile.isEmpty()) {
            String uniqueFileName = fileUtils.saveImage(imagenFile);
            entity.setImagen(uniqueFileName);
        } else {
            entity.setImagen(null);
        }

        // Procesar variaciones
        if (request.getVariaciones() != null && !request.getVariaciones().isEmpty()) {
            for (VariacionProductoRequest varRequest : request.getVariaciones()) {
                VariacionProductoEntity variacionEntity = new VariacionProductoEntity();
                variacionEntity.setAtributos(varRequest.getAtributos());
                // Asumiendo que la VariacionProductoEntity solo tiene atributos, id y relación al producto.
                // Si tuviera SKU/precio/stock propios, se setearían aquí.

                variacionEntity.setProducto(entity); // Establecer la relación
                entity.getVariaciones().add(variacionEntity); // Añadir a la lista del producto para CascadeType.ALL
            }
        }
        
        ProductoEntity productoGuardado = productoRepository.save(entity);
        log.info("Producto con variaciones guardado: {}", productoGuardado.getId());
        
        return mapToResponse(productoGuardado);
    }

    public ProductoResponse updateProducto(ProductoUpdateRequest request, MultipartFile imagenFile) {
        log.info("Actualizando producto con id: {}", request.getId());

        ProductoEntity existing = productoRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + request.getId()));

        if (request.getNombre() != null) {
            existing.setNombre(request.getNombre());
        }
        if (request.getDescripcion() != null) {
            existing.setDescripcion(request.getDescripcion());
        }
        if (request.getPrecio() != null) {
            existing.setPrecio(request.getPrecio());
        }
        if (request.getStock() != null) {
            existing.setStock(request.getStock());
        }
        if (request.getCodigoBarras() != null) {
            existing.setCodigoBarras(request.getCodigoBarras());
        }
        if (request.getSku() != null) {
            existing.setSku(request.getSku());
        }
        if (imagenFile != null && !imagenFile.isEmpty()) {
            String uniqueFileName = fileUtils.saveImage(imagenFile);
            existing.setImagen(uniqueFileName);
        }
        if (request.getEstatus() != null) {
            existing.setEstatus(request.getEstatus());
        }

        existing = productoRepository.save(existing);
        log.info("Producto actualizado: {}", existing);
        return mapToResponse(existing);
    }

    public ProductoResponse getProductoById(Integer id) {
        ProductoEntity entity = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        return mapToResponse(entity);
    }

    public Page<ProductoResponse> getAllProductos(Pageable pageable) {
        return productoRepository.findAll(pageable).map(this::mapToResponse);
    }

    public void deleteProducto(Integer id) {
        ProductoEntity existing = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado con id: " + id));
        productoRepository.delete(existing);
    }

    private ProductoResponse mapToResponse(ProductoEntity entity) {
        List<VariacionProductoResponse> variacionesResponse = Collections.emptyList(); // Inicializar por defecto
        if (entity.getVariaciones() != null && !entity.getVariaciones().isEmpty()) {
            variacionesResponse = entity.getVariaciones().stream()
                    .map(varEntity -> VariacionProductoResponse.builder()
                            .id(varEntity.getId())
                            .atributos(varEntity.getAtributos())
                            .build())
                    .collect(Collectors.toList());
        }

        return ProductoResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .precio(entity.getPrecio())
                .stock(entity.getStock())
                .codigoBarras(entity.getCodigoBarras())
                .imagen(entity.getImagen())
                .sku(entity.getSku())
                .estatus(entity.getEstatus())
                .variaciones(variacionesResponse) // Campo añadido
                .build();
    }

    public Page<ProductoResponse> getProductos(ProductoFilterDto filter, Pageable pageable) {
        Specification<ProductoEntity> specification = ProductoSpecification.filter(filter);
        Page<ProductoEntity> productoEntities = productoRepository.findAll(specification, pageable);
        List<ProductoResponse> dtoToList = mapEntitiesToDto(productoEntities.getContent());
        return new PageImpl<>(dtoToList, pageable, productoEntities.getTotalElements());
    }

    private List<ProductoResponse> mapEntitiesToDto(List<ProductoEntity> entities) {
        return entities.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public void ajustarStock(AjusteStockRequest solicitud) {
        for (ItemAjusteStockRequest item : solicitud.getItems()) {
            ProductoEntity producto = productoRepository.findById(item.getIdProducto())
                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado: " + item.getIdProducto()));
            int nuevoStock = producto.getStock() + item.getAjuste();
            producto.setStock(nuevoStock);
            productoRepository.save(producto);
        }
    }
}
