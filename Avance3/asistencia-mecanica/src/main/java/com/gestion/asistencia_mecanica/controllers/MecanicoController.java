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

    private final MecanicoRepository mecanicoRepository;

    public MecanicoController(MecanicoRepository mecanicoRepository) {
        this.mecanicoRepository = mecanicoRepository;
    }

    @GetMapping("/{ciudad}")
    public ResponseEntity<List<Mecanico>> obtenerMecanicosPorCiudad(@PathVariable String ciudad) {
        List<Mecanico> mecanicos = mecanicoRepository.findByCiudadAndDisponible(ciudad, true);
        return ResponseEntity.ok(mecanicos);
    }

    @PostMapping("/agregar")
    public ResponseEntity<Mecanico> agregarMecanico(@RequestBody Mecanico mecanico) {
        Mecanico nuevoMecanico = mecanicoRepository.save(mecanico);
        return ResponseEntity.ok(nuevoMecanico);
    }

}
