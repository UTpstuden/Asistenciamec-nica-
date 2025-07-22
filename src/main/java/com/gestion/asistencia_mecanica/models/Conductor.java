package com.gestion.asistencia_mecanica.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data // Genera getters, setters, toString, equals y hashCode
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor // Genera un constructor sin argumentos
@SuperBuilder
@Entity
@Table(name = "conductor")
@PrimaryKeyJoinColumn(name = "user_id")
public class Conductor extends User {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "vehiculo_id")
    private Vehiculo vehiculo;

}