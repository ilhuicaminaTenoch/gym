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
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "INTENTO_ACCESO_FALLIDO")
public class IntentoAccesoFallidoEntity extends EntidadEditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDispositivo")
    private Integer id;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private ClienteEntity idCliente;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private DispositivoBiometricoEntity idDispositivoBiometrico;

    @Column(name = "metodo_autenticacion", length = 50)
    private String metodoAutenticacion;

    @Column(name = "motivo", length = 255)
    private String motivo;
}
