package com.gestion.asistencia_mecanica.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gestion.asistencia_mecanica.dto.registration.MecanicoRegistrationDTO;
import com.gestion.asistencia_mecanica.dto.registration.TallerRegistrationDTO;
import com.gestion.asistencia_mecanica.dto.response.MecanicoResponseDTO;
import com.gestion.asistencia_mecanica.dto.response.TallerResponseDTO;
import com.gestion.asistencia_mecanica.models.Mecanico;
import com.gestion.asistencia_mecanica.models.Taller;

@Mapper(componentModel = "spring")
public interface MecanicoMapper {

    @Mapping(target = "taller", source = "taller")
    @Mapping(target = "rol", ignore = true) // Ignorar el rol al crear la entidad
    @Mapping(target = "userId", ignore = true) // Ignorar este campo si no se proporciona en el DTO
    Mecanico toEntity(MecanicoRegistrationDTO dto);

    // Mapper auxiliar para composición
    @Mapping(target = "tallerId", ignore = true) // Ignorar si no se usa
    Taller toEntity(TallerRegistrationDTO dto);

    // Este método convierte Entity a DTO de respuesta
    // Entity → ResponseDTO
    @Mapping(target = "taller", source = "taller")
    MecanicoResponseDTO toResponseDTO(Mecanico mecanico);

    // Entity → TallerResponseDTO (composición)
    TallerResponseDTO toTallerResponseDTO(Taller taller);
}
