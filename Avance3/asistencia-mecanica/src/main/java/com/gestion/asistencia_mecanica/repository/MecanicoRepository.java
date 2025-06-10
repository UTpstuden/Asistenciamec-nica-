package com.gestion.asistencia_mecanica.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.model.Mecanico;
import java.util.List;

@Repository
public interface MecanicoRepository extends JpaRepository<Mecanico, Long> {
    List<Mecanico> findByCiudadAndDisponible(String ciudad, boolean disponible);
}
