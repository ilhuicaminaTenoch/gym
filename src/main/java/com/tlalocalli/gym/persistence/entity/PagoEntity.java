package com.tlalocalli.gym.persistence.entity;

import com.tlalocalli.gym.persistence.audit.EntidadEditable;
import com.tlalocalli.gym.persistence.enums.MetodoPago;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "PAGO")
public class PagoEntity extends EntidadEditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPago")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_caja", nullable = false)
    private CajaEntity caja;

    @ManyToOne
    @JoinColumn(name = "idSuscripcion")
    private SuscripcionEntity suscripcion;

    @ManyToOne
    @JoinColumn(name = "idVenta", nullable = false)
    @ToString.Exclude
    private VentaEntity venta;

    @Column(name = "monto", precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(name = "fechaPago")
    private LocalDateTime fechaPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodoPago", length = 50)
    private MetodoPago metodoPago;

    @Column(name = "numTransaccion", length = 100, insertable = false, updatable = false)
    // Actualiza el campo actumaticamente cuando crea la transaccion desde la el trigger de ps
    @org.hibernate.annotations.Generated(org.hibernate.annotations.GenerationTime.ALWAYS)
    private String numTransaccion;

    @ManyToOne
    @JoinColumn(name = "idPromocion")
    private PromocionEntity promocion;
}
