package com.tlalocalli.gym.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CajaSecuenciaId implements Serializable {
    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "id_caja")
    private Integer idCaja;
}
