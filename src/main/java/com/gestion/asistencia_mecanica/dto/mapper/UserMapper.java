package com.gestion.asistencia_mecanica.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.gestion.asistencia_mecanica.dto.registration.UserRegistrationDTO;
import com.gestion.asistencia_mecanica.dto.response.UserResponseDTO;
import com.gestion.asistencia_mecanica.models.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "rol", ignore = true)
    User toEntity(UserRegistrationDTO dto);

    UserResponseDTO toResponseDTO(User user);
}