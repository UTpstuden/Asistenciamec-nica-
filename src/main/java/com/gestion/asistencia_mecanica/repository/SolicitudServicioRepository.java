package com.gestion.asistencia_mecanica.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gestion.asistencia_mecanica.models.SolicitudServicio;

@Repository
public interface SolicitudServicioRepository extends JpaRepository<SolicitudServicio, Integer> {

    List<SolicitudServicio> findByIdCliente(String idCliente);

    List<SolicitudServicio> findByIdMecanico(String idMecanico);

}
