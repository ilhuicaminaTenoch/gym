package com.tlalocalli.gym.persistence.entity;

import com.tlalocalli.gym.persistence.audit.EntidadEditable;
import com.tlalocalli.gym.persistence.enums.TipoPlan;
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
@ToString(exclude = {"pagos"})
@Entity
@Table(name = "PROMOCION")
public class PromocionEntity extends EntidadEditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idPromocion")
    private Integer id;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "descuento", precision = 10, scale = 2)
    private BigDecimal descuento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipoPlan", length = 100)
    private TipoPlan tipoPlan;

    @Column(name = "vigenciaInicio")
    private LocalDateTime vigenciaInicio;

    @Column(name = "vigenciaFin")
    private LocalDateTime vigenciaFin;

}
