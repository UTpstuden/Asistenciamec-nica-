package com.gestion.asistencia_mecanica.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SolicitudServicio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_Solicitud")
    private Integer idSolicitud;
    private String idCliente;
    private String idMecanico;
    private String descripcion;
    private String estado;
    private long fechaCreacion;
    private String lugar;
    private Double latitud;
    private Double longitud;

    private Integer calificacion;
}
