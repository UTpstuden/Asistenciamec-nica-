package com.gestion.asistencia_mecanica.dto.registration;

import com.gestion.asistencia_mecanica.models.Taller.TipoServicio;
import com.gestion.asistencia_mecanica.validation.SoloLetras;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TallerRegistrationDTO {

    @NotBlank(message = "El nombre del taller es obligatorio")
    @SoloLetras
    private String nombre;

    @NotBlank(message = "La dirección es obligatoria")
    private String direccion;

    @NotBlank(message = "El distrito es obligatorio")
    private String distrito;

    @NotBlank(message = "El radio de cobertura es obligatorio")
    @Min(value = 1, message = "El radio debe ser mayor que 0")
    private Integer radioCobertura;

    @NotNull(message = "El tipo de servicio es obligatorio")
    private TipoServicio tipoServicio;
}
