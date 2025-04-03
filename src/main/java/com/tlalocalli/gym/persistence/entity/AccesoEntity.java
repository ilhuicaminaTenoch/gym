package com.tlalocalli.gym.persistence.entity;

import com.tlalocalli.gym.persistence.audit.EntidadEditable;
import com.tlalocalli.gym.persistence.enums.MetodoAcceso;
import jakarta.persistence.*;
import lombok.*;

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
@Table(name = "ACCESO")
public class AccesoEntity extends EntidadEditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idAcceso")
    private Integer id;

    @OneToMany(mappedBy = "acceso")
    private List<DispositivoBiometricoEntity> dispositivoBiometrico = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private ClienteEntity cliente;

    @Column(name = "fechaAcceso")
    private LocalDateTime fechaAcceso;

    @Column(name = "fechaSalida")
    private LocalDateTime fechaSalida;

    @Enumerated(EnumType.STRING)
    @Column(name = "metodoAcceso", length = 50)
    private MetodoAcceso metodoAcceso;

    @Column(name = "exitoso", nullable = false)
    private Boolean exitoso = Boolean.FALSE;

    @Enumerated(EnumType.STRING)
    @Column(name ="metodo_autenticacion", length = 50)
    private MetodoAcceso metodo_autenticacion;
}
