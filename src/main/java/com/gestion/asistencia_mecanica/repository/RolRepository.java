package com.gestion.asistencia_mecanica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion.asistencia_mecanica.models.Rol;
import com.gestion.asistencia_mecanica.models.Rol.RolNombre;

public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByNombre(RolNombre nombre);

    boolean existsByNombre(RolNombre nombre);
}