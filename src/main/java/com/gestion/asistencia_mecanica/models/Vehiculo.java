package com.gestion.asistencia_mecanica.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "Vehiculo")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehiculo_id")
    private Integer vehiculoId;

    @Column(name = "marca", nullable = false)
    private String marca;

    @Column(name = "modelo", nullable = false)
    private String modelo;

    @Column(name = "año", nullable = false)
    private Integer año;

    @Column(name = "placa", nullable = false, unique = true)
    private String placa;

    @Column(name = "color", nullable = false)
    private String color;

    @Column(name = "tipo_vehiculo", nullable = false)
    private String tipoVehiculo;

}
