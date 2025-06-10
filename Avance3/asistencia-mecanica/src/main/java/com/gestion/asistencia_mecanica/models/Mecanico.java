package com.gestion.asistencia_mecanica.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // Genera getters, setters, toString, equals y hashCode
@NoArgsConstructor // Genera un constructor sin argumentos
@AllArgsConstructor // Genera un constructor con todos los argumentos
@Builder // Permite construir objetos de forma fluida
@Entity
@Table(name = "Mecanico")
public class Mecanico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mecanico_id")
    private Long mecanicoId;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "experiencia", nullable = false) 
    private Integer experiencia;

    @Column(name = "email", unique = true, length = 150) 
    private String email;

    @Column(name = "telefono", length = 15) 
    private String telefono;

    @Column(name = "calificacion_promedio", precision = 3, scale = 2) 
    private BigDecimal calificacionPromedio;

    @Column(name = "ciudad", nullable = false, length = 50)
    private String ciudad;

    @Column(name = "disponible", nullable = false) 
    private Boolean disponible;

}
