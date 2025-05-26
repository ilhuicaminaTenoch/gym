package com.tlalocalli.gym.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tlalocalli.gym.persistence.dto.request.AjusteStockRequest;
import com.tlalocalli.gym.persistence.dto.request.VariacionProductoRequest;
import com.tlalocalli.gym.persistence.entity.ProductoEntity;
import com.tlalocalli.gym.persistence.entity.VariacionProductoEntity;
import com.tlalocalli.gym.persistence.repository.ProductoRepository;
import com.tlalocalli.gym.persistence.repository.VariacionProductoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VariacionProductoService {

    private final ProductoRepository productoRepo;
    private final VariacionProductoRepository variacionRepo;
    private final ObjectMapper objectMapper;

    @Transactional
    public VariacionProductoEntity crearVariacion(Integer productoId, VariacionProductoRequest req) {
        ProductoEntity p = productoRepo.findById(productoId)
                .orElseThrow(() -> new EntityNotFoundException("Producto no existe"));
        VariacionProductoEntity v = new VariacionProductoEntity();
        v.setProducto(p);
        v.setSku(req.getSku());
        v.setPrecio(req.getPrecio());
        v.setStock(req.getStock());
        v.setImagenUrl(req.getImagenUrl());
        try {
            v.setAtributosJson(objectMapper.writeValueAsString(req.getAtributos()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al procesar atributos de variación", e);
        }
        return variacionRepo.save(v);
    }

    public List<VariacionProductoEntity> listarVariaciones(Integer productoId) {
        return variacionRepo.findByProductoId(productoId);
    }

    public VariacionProductoEntity obtenerVariacion(Integer id) {
        return variacionRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Variación no encontrada"));
    }

    @Transactional
    public VariacionProductoEntity actualizarVariacion(Integer id, VariacionProductoRequest req) {
        VariacionProductoEntity v = obtenerVariacion(id);
        v.setSku(req.getSku());
        v.setPrecio(req.getPrecio());
        v.setStock(req.getStock());
        v.setImagenUrl(req.getImagenUrl());
        try {
            v.setAtributosJson(objectMapper.writeValueAsString(req.getAtributos()));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al procesar atributos de variación", e);
        }
        return variacionRepo.save(v);
    }

    @Transactional
    public void eliminarVariacion(Integer id) {
        variacionRepo.delete(obtenerVariacion(id));
    }

    @Transactional
    public void ajustarStock(AjusteStockRequest req) {
        VariacionProductoEntity variacion = variacionRepo.findById(req.getVariacionId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Variación no encontrada con id " + req.getVariacionId()));

        int nuevoStock = variacion.getStock() - req.getCantidadComprada();
        if (nuevoStock < 0) {
            throw new RuntimeException(
                    "Stock insuficiente en la variación " + variacion.getSku() +
                            ". Disponible: " + variacion.getStock() +
                            ", solicitado: " + req.getCantidadComprada());
        }
        variacion.setStock(nuevoStock);
        variacionRepo.save(variacion);
    }
}
