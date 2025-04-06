package com.tlalocalli.gym.persistence.entity;

import com.tlalocalli.gym.persistence.audit.EntidadEditable;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Check;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"cliente"})
@Entity
@Check(constraints = "fecha_fin > fecha_inicio")
@Table(name = "SUSCRIPCION")
@Builder
public class SuscripcionEntity extends EntidadEditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idSuscripcion")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private ClienteEntity cliente;

    @OneToOne
    @JoinColumn(name = "idPlan")
    private PlanSuscripcionEntity plan;

    @ManyToOne
    @JoinColumn(name = "idPromocion")
    private PromocionEntity promocion;

    @Column(name = "fechaInicio")
    private LocalDateTime fechaInicio;

    @Column(name = "fechaFin")
    private LocalDateTime fechaFin;

    @Column(name = "estado", length = 50)
    private String estado;

}
