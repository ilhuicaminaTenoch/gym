package com.tlalocalli.gym.persistence.entity;

import com.tlalocalli.gym.persistence.audit.EntidadEditable;
import com.tlalocalli.gym.persistence.enums.Estado;
import com.tlalocalli.gym.persistence.enums.MetodoAcceso;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "DISPOSITIVO_BIOMETRICO")
public class DispositivoBiometricoEntity extends EntidadEditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDispositivo")
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", length = 50)
    private MetodoAcceso tipo;

    @Column(name = "ubicacion", length = 200)
    private String ubicacion;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 50)
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "idAcceso", nullable = false)
    private AccesoEntity acceso;

    @ManyToOne
    private IntentoAccesoFallidoEntity intentoAccesoFallido;
}
