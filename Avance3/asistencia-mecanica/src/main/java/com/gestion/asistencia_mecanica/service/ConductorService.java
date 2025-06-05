package com.gestion.asistencia_mecanica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.asistencia_mecanica.exception.ResourceNotFoundException;
import com.gestion.asistencia_mecanica.models.Conductor;
import com.gestion.asistencia_mecanica.repository.ConductorRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConductorService {

    private final ConductorRepository conductorRepository;

    @Transactional(readOnly = true)
    public List<Conductor> getAllConductors() {
        log.debug("Obteniendo lista de todos los conductores");
        return conductorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Conductor> getConductorById(Integer id) {
        log.debug("Buscando conductor con ID: {}", id);
        return conductorRepository.findById(id);
    }

    @Transactional
    public Conductor createConductor(Conductor conductor) {
        log.info("Creando nuevo conductor: {}", conductor.getNombre());
        return conductorRepository.save(conductor);
    }

    @Transactional
    public Conductor updateConductor(Integer id, Conductor conductorDetails) {
        log.info("Actualizando conductor con ID: {}", id);
        return conductorRepository.findById(id)
                .map(conductor -> {
                    log.debug("Datos antes de actualizar: {}", conductor);
                    conductor.setNombre(conductorDetails.getNombre());
                    conductor.setEmail(conductorDetails.getEmail());
                    conductor.setTelefono(conductorDetails.getTelefono());
                    log.debug("Datos actualizados: {}", conductor);
                    return conductorRepository.save(conductor);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Conductor no encontrado con ID: " + id));
    }

    @Transactional
    public void deleteConductor(Integer id) {
        log.warn("Eliminando conductor con ID: {}", id);
        conductorRepository.deleteById(id);
    }
}
