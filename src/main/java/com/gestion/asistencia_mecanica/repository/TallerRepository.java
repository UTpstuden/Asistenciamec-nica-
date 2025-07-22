package com.gestion.asistencia_mecanica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.asistencia_mecanica.models.Taller;

@Repository
public interface TallerRepository extends JpaRepository<Taller, Integer> {
}
