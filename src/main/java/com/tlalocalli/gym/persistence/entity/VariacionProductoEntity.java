package com.tlalocalli.gym.persistence.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tlalocalli.gym.persistence.audit.EntidadEditable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "producto_variacion")
public class VariacionProductoEntity extends EntidadEditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_variacion")
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_producto", nullable = false)
    private ProductoEntity producto;

    @Column(name = "sku", nullable = false, length = 50)
    private String sku;

    @Column(name = "precio", precision = 10, scale = 2)
    private BigDecimal precio;

    @Column(name = "stock")
    private Integer stock;

    @Column(name = "imagen_url", length = 100)
    private String imagenUrl;

    @Column(name = "atributos_json", columnDefinition = "jsonb")
    private String atributosJson;
}
