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

import com.gestion.asistencia_mecanica.models.Mecanico;
import com.gestion.asistencia_mecanica.service.MecanicoService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/mecanicos")
public class MecanicoController {

    private final MecanicoService mecanicoService;

    @GetMapping
    public List<Mecanico> getAllMecanicos() {
        log.debug("Petición GET para obtener todos los mecánicos");
        return mecanicoService.getAllMecanicos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Mecanico> getMecanicoById(@PathVariable Integer id) {
        log.debug("Petición GET para obtener mecánico con ID: {}", id);
        return mecanicoService.getMecanicoById(id)
                .map(mecanico -> {
                    log.info("Mecánico encontrado con ID: {}", id);
                    return ResponseEntity.ok(mecanico);
                })
                .orElseGet(() -> {
                    log.warn("Mecánico no encontrado con ID: {}", id);
                    return ResponseEntity.notFound().build();
                });
    }

    @PostMapping
    public ResponseEntity<Mecanico> createMecanico(@RequestBody Mecanico mecanico) {
        log.info("Petición POST para crear mecánico: {}", mecanico.getNombre());
        Mecanico newMecanico = mecanicoService.createMecanico(mecanico);
        return new ResponseEntity<>(newMecanico, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Mecanico> updateMecanico(@PathVariable Integer id, @RequestBody Mecanico mecanicoDetails) {
        log.info("Petición PUT para actualizar mecánico con ID: {}", id);
        try {
            Mecanico updatedMecanico = mecanicoService.updateMecanico(id, mecanicoDetails);
            return ResponseEntity.ok(updatedMecanico);
        } catch (RuntimeException e) {
            log.error("Error al actualizar mecánico con ID {}: {}", id, e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMecanico(@PathVariable Integer id) {
        log.warn("Petición DELETE para eliminar mecánico con ID: {}", id);
        mecanicoService.deleteMecanico(id);
        return ResponseEntity.noContent().build();
    }
}
