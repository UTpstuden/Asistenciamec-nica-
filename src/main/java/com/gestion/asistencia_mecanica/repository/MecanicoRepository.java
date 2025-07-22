package com.gestion.asistencia_mecanica.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gestion.asistencia_mecanica.models.Mecanico;

@Repository
public interface MecanicoRepository extends JpaRepository<Mecanico, Integer> {
    Optional<Mecanico> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByDni(String dni);

    @Query("SELECT m FROM Mecanico m WHERE m.taller.distrito = :distrito AND m.disponibilidad = true")
    List<Mecanico> findAvailableByDistrito(@Param("distrito") String distrito);

}
