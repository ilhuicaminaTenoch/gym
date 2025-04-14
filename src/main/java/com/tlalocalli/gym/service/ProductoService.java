package com.tlalocalli.gym.service;

import com.tlalocalli.gym.persistence.dto.ProductoFilterDto;
import com.tlalocalli.gym.persistence.dto.request.AjusteStockRequest;
import com.tlalocalli.gym.persistence.dto.request.ItemAjusteStockRequest;
import com.tlalocalli.gym.persistence.entity.ProductoEntity;
import com.tlalocalli.gym.persistence.repository.ProductoRepository;
import com.tlalocalli.gym.persistence.dto.request.ProductoRequest;
import com.tlalocalli.gym.persistence.dto.request.ProductoUpdateRequest;
import com.tlalocalli.gym.persistence.dto.response.ProductoResponse;
import com.tlalocalli.gym.util.FileUtils;
import com.tlalocalli.gym.util.ProductoSpecification;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    public ProductoResponse createProducto(ProductoRequest request, MultipartFile imagenFile) {
        log.info("Creando nuevo producto: {}", request);

        ProductoEntity entity = new ProductoEntity();
        entity.setNombre(request.getNombre());
        entity.setDescripcion(request.getDescripcion());
        entity.setPrecio(request.getPrecio());
        entity.setStock(request.getStock());
        entity.setCodigoBarras(request.getCodigoBarras());
        entity.setSku(request.getSku());

        if (imagenFile != null && !imagenFile.isEmpty()) {
            String uniqueFileName = fileUtils.saveImage(imagenFile);
            entity.setImagen(uniqueFileName);
        } else {
            entity.setImagen(null);
        }

        entity = productoRepository.save(entity);
        return mapToResponse(entity);
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
        return ProductoResponse.builder()
                .id(entity.getId())
                .nombre(entity.getNombre())
                .descripcion(entity.getDescripcion())
                .precio(entity.getPrecio())
                .stock(entity.getStock())
                .codigoBarras(entity.getCodigoBarras())
                .imagen(entity.getImagen())
                .sku(entity.getSku())
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
