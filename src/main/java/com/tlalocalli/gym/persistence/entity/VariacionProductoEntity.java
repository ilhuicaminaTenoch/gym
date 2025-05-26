package com.tlalocalli.gym.persistence.entity;

import com.tlalocalli.gym.persistence.audit.EntidadEditable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap; // Import for initializing the map

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "VARIACION_PRODUCTO")
public class VariacionProductoEntity extends EntidadEditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVariacionProducto")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idProducto", nullable = false)
    private ProductoEntity producto;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "VARIACION_PRODUCTO_ATRIBUTOS", joinColumns = @JoinColumn(name = "idVariacionProducto"))
    @MapKeyColumn(name = "atributo_clave", length = 100)
    @Column(name = "atributo_valor", length = 255)
    private Map<String, String> atributos = new HashMap<>(); // Initialized map
}
