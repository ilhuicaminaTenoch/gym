package com.tlalocalli.gym.persistence.entity;

import com.tlalocalli.gym.persistence.enums.TipoPlan;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name ="PLAN_SUSCRIPCION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanSuscripcionEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_plan")
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "nombre", nullable = false)
    private TipoPlan nombre;

    @Column(name = "costo_base", precision = 10, scale = 2)
    private BigDecimal costoBase;

    private String descripcion;

}
