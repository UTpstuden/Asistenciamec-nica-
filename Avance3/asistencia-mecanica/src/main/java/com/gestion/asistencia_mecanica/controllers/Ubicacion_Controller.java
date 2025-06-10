package com.example.demo.Controlador;

import com.example.demo.Service.GeocodificacionService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/ubicacion")
public class Ubicacion_Controller {
    private final GeocodificacionService geocodificacionService;

    // Constructor con inyección de dependencias
    @Autowired
    public Ubicacion_Controller(GeocodificacionService geocodificacionService) {
        this.geocodificacionService = geocodificacionService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> obtenerCiudad(@RequestBody Map<String, Double> coordenadas) {
        double latitud = coordenadas.get("latitud");
        double longitud = coordenadas.get("longitud");
        String ciudad = geocodificacionService.obtenerCiudad(latitud, longitud);

        Map<String, String> response = new HashMap<>();
        response.put("ciudad", ciudad);
        return ResponseEntity.ok(response);
    }
}