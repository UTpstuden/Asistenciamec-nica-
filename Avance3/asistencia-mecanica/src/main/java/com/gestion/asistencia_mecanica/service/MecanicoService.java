package com.gestion.asistencia_mecanica.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gestion.asistencia_mecanica.exception.ResourceNotFoundException;
import com.gestion.asistencia_mecanica.models.Mecanico;
import com.gestion.asistencia_mecanica.repository.MecanicoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MecanicoService {

    private final MecanicoRepository mecanicoRepository;

    @Transactional(readOnly = true)
    public List<Mecanico> getAllMecanicos() {
        log.debug("Obteniendo lista de todos los mecánicos");
        return mecanicoRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Mecanico> getMecanicoById(Integer id) {
        log.debug("Buscando mecánico con ID: {}", id);
        return mecanicoRepository.findById(id);
    }

    @Transactional
    public Mecanico createMecanico(Mecanico mecanico) {
        log.info("Creando nuevo mecánico: {}", mecanico.getNombre());
        return mecanicoRepository.save(mecanico);
    }

    @Transactional
    public Mecanico updateMecanico(Integer id, Mecanico mecanicoDetails) {
        log.info("Actualizando mecánico con ID: {}", id);
        return mecanicoRepository.findById(id)
                .map(mecanico -> {
                    log.debug("Datos antes de actualizar: {}", mecanico);
                    mecanico.setNombre(mecanicoDetails.getNombre());
                    mecanico.setExperiencia(mecanicoDetails.getExperiencia());
                    mecanico.setEmail(mecanicoDetails.getEmail());
                    mecanico.setTelefono(mecanicoDetails.getTelefono());
                    mecanico.setCalificacionPromedio(mecanicoDetails.getCalificacionPromedio());
                    log.debug("Datos actualizados: {}", mecanico);
                    return mecanicoRepository.save(mecanico);
                })
                .orElseThrow(() -> new ResourceNotFoundException("Mecánico no encontrado con ID: " + id));
    }

    @Transactional
    public void deleteMecanico(Integer id) {
        log.warn("Eliminando mecánico con ID: {}", id);
        mecanicoRepository.deleteById(id);
    }
}
