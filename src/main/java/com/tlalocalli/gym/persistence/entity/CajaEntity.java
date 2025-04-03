package com.tlalocalli.gym.persistence.entity;

import com.tlalocalli.gym.persistence.audit.EntidadEditable;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CAJA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CajaEntity extends EntidadEditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_caja")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "prefijo", nullable = false, length = 20)
    @Builder.Default
    private String prefijo = "CASH";
}
