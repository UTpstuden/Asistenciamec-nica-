package com.gestion.asistencia_mecanica.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.gestion.asistencia_mecanica.models.Rol;
import com.gestion.asistencia_mecanica.repository.RolRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepository;

    @Override
    public void run(String... args) throws Exception {
        for (Rol.RolNombre rolNombre : Rol.RolNombre.values()) {
            boolean exists = rolRepository.existsByNombre(rolNombre);
            if (!exists) {
                rolRepository.save(new Rol(null, rolNombre));
                System.out.println("Rol creado: " + rolNombre);
            }
        }
    }
}
