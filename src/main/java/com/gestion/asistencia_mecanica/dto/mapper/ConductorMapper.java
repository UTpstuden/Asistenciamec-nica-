package com.gestion.asistencia_mecanica.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gestion.asistencia_mecanica.dto.registration.ConductorRegistrationDTO;
import com.gestion.asistencia_mecanica.dto.registration.VehiculoRegistrationDTO;
import com.gestion.asistencia_mecanica.dto.response.ConductorResponseDTO;
import com.gestion.asistencia_mecanica.dto.response.VehiculoResponseDTO;
import com.gestion.asistencia_mecanica.models.Conductor;
import com.gestion.asistencia_mecanica.models.Vehiculo;

@Mapper(componentModel = "spring")
public interface ConductorMapper {

    // DTO → Entity (Conductor con Vehiculo)
    @Mapping(target = "vehiculo", source = "vehiculo")
    @Mapping(target = "rol", ignore = true) // Ignorar el rol al crear la entidad
    @Mapping(target = "userId", ignore = true) // Ignorar el ID al crear la entidad
    Conductor toEntity(ConductorRegistrationDTO dto);

    // VehiculoDTO → Vehiculo (mapeo auxiliar)
    @Mapping(target = "vehiculoId", ignore = true) // Ignorar el ID al crear la entidad
    Vehiculo toEntity(VehiculoRegistrationDTO dto);

    // Entity → DTO (respuesta para cliente)
    @Mapping(target = "vehiculo", source = "vehiculo")
    ConductorResponseDTO toResponseDTO(Conductor conductor);

    // Vehiculo → VehiculoResponseDTO (composición auxiliar)
    VehiculoResponseDTO toVehiculoResponseDTO(Vehiculo vehiculo);
}
