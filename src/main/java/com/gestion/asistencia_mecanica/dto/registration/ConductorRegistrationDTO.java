package com.gestion.asistencia_mecanica.dto.registration;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ConductorRegistrationDTO extends UserRegistrationDTO {

    @NotNull(message = "El vehículo no puede ser nulo.")
    private VehiculoRegistrationDTO vehiculo;
}
