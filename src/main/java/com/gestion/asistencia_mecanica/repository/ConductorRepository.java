package com.gestion.asistencia_mecanica.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.asistencia_mecanica.models.Conductor;

@Repository
public interface ConductorRepository extends JpaRepository<Conductor, Integer> {
     Optional<Conductor> findByEmail(String email);

     boolean existsByEmail(String email);

     boolean existsByDni(String dni);
}
