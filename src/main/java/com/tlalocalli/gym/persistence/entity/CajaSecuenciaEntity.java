package com.tlalocalli.gym.persistence.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;


@Entity
@Table(name = "CAJA_SECUENCIA")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CajaSecuenciaEntity implements Serializable {

    @EmbeddedId
    private CajaSecuenciaId id;

    @Column(name = "secuencia", nullable = false)
    private Integer secuencia;
}
