package com.tlalocalli.gym.persistence.entity;

import com.tlalocalli.gym.persistence.audit.EntidadEditable;
import com.tlalocalli.gym.persistence.enums.MetodoPago;
import com.tlalocalli.gym.persistence.enums.TipoVenta;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"cliente", "detalleVentas", "suscripcion", "caja"})
@Entity
@Builder
@Table(name = "VENTA", indexes = {
        @Index(name = "idx_venta_fecha", columnList = "fechaVenta")
})
public class VentaEntity extends EntidadEditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idVenta")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private ClienteEntity cliente;

    @ManyToOne
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuarioEntity usuario;

    @ManyToOne
    @JoinColumn(name = "idTurno", nullable = false)
    private TurnoEntity turno;

    @Column(name = "fechaVenta")
    private LocalDateTime fechaVenta;

    @Column(name = "total", precision = 10, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodoPago", length = 50)
    private MetodoPago metodoPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_venta", nullable = false, length = 50, columnDefinition = "varchar(50) default 'producto'")
    private TipoVenta tipoVenta;

    @Column(name = "num_transaccion", length = 100)
    private String numTransaccion;

    // Relaciones
    @ManyToOne
    @JoinColumn(name = "id_suscripcion")
    private SuscripcionEntity suscripcion;

    @ManyToOne
    @JoinColumn(name = "id_caja")
    private CajaEntity caja;

    @OneToMany(mappedBy = "venta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetalleVentaEntity> detalleVentas = new ArrayList<>();
}
