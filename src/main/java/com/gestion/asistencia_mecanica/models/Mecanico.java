package com.gestion.asistencia_mecanica.models;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "mecanico")
@PrimaryKeyJoinColumn(name = "user_id")
public class Mecanico extends User {

    @Column(name = "experiencia", nullable = false)
    private String experiencia;

    @Column(name = "especialidad")
    private String especialidad;

    @Column(name = "certificaciones", columnDefinition = "TEXT")
    private String certificaciones;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "calificacion_promedio", precision = 3, scale = 2)
    private BigDecimal calificacionPromedio;

    @ManyToOne(cascade = CascadeType.PERSIST) 
    @JoinColumn(name = "taller_id")
    private Taller taller;

    @Column(name = "disponibilidad", nullable = false)
    private boolean disponibilidad = true;
}
