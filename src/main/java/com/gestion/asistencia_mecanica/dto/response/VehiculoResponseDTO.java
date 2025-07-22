package com.gestion.asistencia_mecanica.dto.response;

import lombok.Data;

@Data
public class VehiculoResponseDTO {
    private String marca;
    private String modelo;
    private Integer año;
    private String placa;
    private String color;
    private String tipoVehiculo;
}
