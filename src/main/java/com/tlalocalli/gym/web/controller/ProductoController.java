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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/api/productos")
@RequiredArgsConstructor
public class ProductoController {

    private final ProductoService productoService;


    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductoResponse> createProducto(
            @RequestPart("producto") @Valid ProductoRequest request,
            @RequestPart(value = "imagen", required = false) MultipartFile imagenFile) {

        ProductoResponse response = productoService.createProducto(request, imagenFile);
        return ResponseEntity.ok(response);
    }

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductoResponse> updateProducto(
            @RequestPart("producto") @Valid ProductoUpdateRequest request,
            @RequestPart(value = "imagen", required = false) MultipartFile imagenFile) {
        ProductoResponse response = productoService.updateProducto(request, imagenFile);
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
