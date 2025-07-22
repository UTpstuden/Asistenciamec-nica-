package com.gestion.asistencia_mecanica.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Taller")
public class Taller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "taller_id")
    private Integer tallerId;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "distrito")
    private String distrito;

    @Column(name = "radio_cobertura")
    private Integer radioCobertura;

    @Column(name = "tipo_servicio")
    @Enumerated(EnumType.STRING)
    private TipoServicio tipoServicio;

    public enum TipoServicio {
        MOVIL,
        TALLER,
        AMBOS
    }
}
