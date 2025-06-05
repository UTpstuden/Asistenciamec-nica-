package com.gestion.asistencia_mecanica.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gestion.asistencia_mecanica.models.Conductor;
import com.gestion.asistencia_mecanica.service.ConductorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/conductores")
public class ConductorController {

    private final ConductorService conductorService;

    @GetMapping
    public List<Conductor> getAllConductors() {
        log.debug("Petición GET para obtener todos los conductores");
        return conductorService.getAllConductors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conductor> getConductorById(@PathVariable Integer id) {
        log.debug("Petición GET para obtener conductor con ID: {}", id);
        return conductorService.getConductorById(id)
                .map(conductor -> {
                    log.info("Conductor encontrado con ID: {}", id);
                    return ResponseEntity.ok(conductor);
                })
                .orElseGet(() -> {
                    log.warn("Conductor no encontrado con ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<Conductor> createConductor(@RequestBody Conductor conductor) {
        log.info("Petición POST para crear conductor: {}", conductor.getNombre());
        Conductor newConductor = conductorService.createConductor(conductor);
        return new ResponseEntity<>(newConductor, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conductor> updateConductor(@PathVariable Integer id,
            @RequestBody Conductor conductorDetails) {
        log.info("Petición PUT para actualizar conductor con ID: {}", id);
        try {
            Conductor updatedConductor = conductorService.updateConductor(id, conductorDetails);
            return ResponseEntity.ok(updatedConductor);
        } catch (RuntimeException e) {
            log.error("Error al actualizar conductor con ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConductor(@PathVariable Integer id) {
        log.warn("Petición DELETE para eliminar conductor con ID: {}", id);
        conductorService.deleteConductor(id);
        return ResponseEntity.noContent().build();
    }
}
