package com.gestion.asistencia_mecanica.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudServicio {
    private String idSolicitud;
    private String idCliente;
    private String idMecanico;
    private String descripcion;
    private String estado;
    private long fechaCreacion;
}