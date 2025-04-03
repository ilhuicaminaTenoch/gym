package com.tlalocalli.gym.persistence.entity;

import com.tlalocalli.gym.persistence.audit.EntidadEditable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "DETALLE_VENTA")
public class DetalleVentaEntity extends EntidadEditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalle")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idVenta", nullable = false)
    @ToString.Exclude
    private VentaEntity venta;

    @ManyToOne
    @JoinColumn(name = "idProducto", nullable = false)
    private ProductoEntity producto;

    @ManyToOne
    @JoinColumn(name = "idPromocion")
    private PromocionEntity promocion;

    @Column(name = "cantidad")
    private Integer cantidad;

    @Column(name = "precioUnitario", precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    @Column(name = "subtotal", precision = 10, scale = 2)
    private BigDecimal subtotal;
}
