package com.gestion.asistencia_mecanica.service.implementation;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.asistencia_mecanica.dto.mapper.UserMapper;
import com.gestion.asistencia_mecanica.dto.registration.UserRegistrationDTO;
import com.gestion.asistencia_mecanica.dto.response.UserResponseDTO;
import com.gestion.asistencia_mecanica.models.Rol;
import com.gestion.asistencia_mecanica.models.Rol.RolNombre;
import com.gestion.asistencia_mecanica.models.User;
import com.gestion.asistencia_mecanica.repository.RolRepository;
import com.gestion.asistencia_mecanica.repository.UserRepository;
import com.gestion.asistencia_mecanica.service.interfaces.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepo;
    private final RolRepository rolRepo;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User register(UserRegistrationDTO dto, RolNombre rolNombre) {
        if (userRepo.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo electrónico");
        }

        Rol rol = rolRepo.findByNombre(rolNombre)
                .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado"));

        User user = userMapper.toEntity(dto);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRol(rol);
        return userRepo.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO login(String email, String password) {
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        // aquí verificarías password con passwordEncoder.matches(...)
        return userMapper.toResponseDTO(user);
    }
}
