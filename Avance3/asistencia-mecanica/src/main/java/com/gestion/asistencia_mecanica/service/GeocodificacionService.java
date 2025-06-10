package com.example.demo.Service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class GeocodificacionService {
    public String obtenerCiudad(double latitud, double longitud) {
        String url = "https://api.bigdatacloud.net/data/reverse-geocode-client?latitude=" + latitud + "&longitude=" + longitud + "&localityLanguage=es";
        RestTemplate restTemplate = new RestTemplate();

        // Utilizamos ParameterizedTypeReference para especificar el tipo genérico
        Map<String, Object> response = restTemplate.exchange(
                url,
                org.springframework.http.HttpMethod.GET,
                null,
                new ParameterizedTypeReference<Map<String, Object>>() {
                }).getBody();

        if (response != null && response.containsKey("city")) {
            return response.get("city").toString();
        }

        return "Ciudad desconocida";
    }
}
