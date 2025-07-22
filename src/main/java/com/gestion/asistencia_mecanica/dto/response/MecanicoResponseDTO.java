package com.gestion.asistencia_mecanica.dto.response;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;

import com.gestion.asistencia_mecanica.models.SolicitudServicio;

@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class MecanicoResponseDTO extends UserResponseDTO {
    private String experiencia;
    private String especialidad;
    private BigDecimal calificacionPromedio;
    private TallerResponseDTO taller;

    // New fields for statistics
    private Integer totalServicios;
    private Integer serviciosCompletados;
    private Integer serviciosEsteMes;
    private Double tiempoPromedioAtencion;

    private Integer solicitudesActivas;
    private BigDecimal ingresosHoy;
    private BigDecimal ingresosSemana;
    private BigDecimal ingresosMes;
    private List<SolicitudServicio> serviciosRecientes;

    private BigDecimal calificacionSum;
}
