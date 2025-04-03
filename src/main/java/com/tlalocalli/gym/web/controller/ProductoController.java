package com.tlalocalli.gym.web.controller;

import com.tlalocalli.gym.persistence.dto.ProductoFilterDto;
import com.tlalocalli.gym.persistence.dto.request.AjusteStockRequest;
import com.tlalocalli.gym.service.ProductoService;
import com.tlalocalli.gym.persistence.dto.request.ProductoRequest;
import com.tlalocalli.gym.persistence.dto.request.ProductoUpdateRequest;
import com.tlalocalli.gym.persistence.dto.response.ProductoResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;

    @PostMapping
    public ResponseEntity<ProductoResponse> createProducto(@Valid @RequestBody ProductoRequest request) {
        ProductoResponse response = productoService.createProducto(request);
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<ProductoResponse> updateProducto(@Valid @RequestBody ProductoUpdateRequest request) {
        ProductoResponse response = productoService.updateProducto(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProductoResponse>> searchProductos(
            @RequestParam(value = "nombre", required = false) String nombre,
            @RequestParam(value = "codigoBarras", required = false) String codigoBarras,
            Pageable pageable) {
        ProductoFilterDto filterDto = new ProductoFilterDto(nombre, codigoBarras);
        return ResponseEntity.ok(productoService.getProductos(filterDto, pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProducto(@PathVariable("id") Integer id) {
        productoService.deleteProducto(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/ajustar")
    public ResponseEntity<Void> ajustarStock(@Valid @RequestBody AjusteStockRequest solicitud) {
        productoService.ajustarStock(solicitud);
        return ResponseEntity.noContent().build();
    }
}
