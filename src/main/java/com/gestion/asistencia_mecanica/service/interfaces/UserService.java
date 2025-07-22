package com.gestion.asistencia_mecanica.service.interfaces;

import com.gestion.asistencia_mecanica.dto.registration.UserRegistrationDTO;
import com.gestion.asistencia_mecanica.dto.response.UserResponseDTO;
import com.gestion.asistencia_mecanica.models.Rol.RolNombre;
import com.gestion.asistencia_mecanica.models.User;

public interface UserService {
  User register(UserRegistrationDTO dto, RolNombre rol);

  UserResponseDTO login(String email, String password);

}
