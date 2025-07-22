package com.gestion.asistencia_mecanica.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pago {
    private String idPago;
    private String idSolicitudServicio;
    private double monto;
    private String metodo;
    private String estado;
    private long fechaPago;
}