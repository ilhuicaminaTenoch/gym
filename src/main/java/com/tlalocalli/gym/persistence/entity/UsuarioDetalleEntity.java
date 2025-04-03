package com.tlalocalli.gym.persistence.entity;

import com.tlalocalli.gym.persistence.audit.EntidadEditable;
import com.tlalocalli.gym.persistence.enums.Genero;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "USUARIO_DETALLE")
public class UsuarioDetalleEntity extends EntidadEditable implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetalle")
    private Integer id;

    // Relación con USUARIO
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idUsuario", nullable = false)
    private UsuarioEntity usuario;

    @Column(name = "nombreCompleto", length = 200)
    private String nombreCompleto;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "telefono", length = 50)
    private String telefono;

    @Column(name = "fechaNacimiento")
    private LocalDateTime fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "genero", length = 50)
    private Genero genero = Genero.OTRO; // Valor por defecto
}
