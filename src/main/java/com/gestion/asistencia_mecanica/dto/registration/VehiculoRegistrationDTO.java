package com.gestion.asistencia_mecanica.dto.registration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VehiculoRegistrationDTO {

    @NotBlank(message = "La marca es obligatoria")
    private String marca;

    @NotBlank(message = "El modelo es obligatorio")
    private String modelo;

    @NotNull(message = "El año no puede estar vacío.")
    @Min(value = 1990, message = "El año debe ser igual o posterior a 1990.")
    @Max(value = 2024, message = "El año no puede ser posterior al año actual (2025).")
    private Integer año;

    @NotBlank(message = "La placa es obligatoria")
    private String placa;

    @NotBlank(message = "El color es obligatorio")
    private String color;

    @NotBlank(message = "El tipo de vehículo es obligatorio")
    private String tipoVehiculo;
}
