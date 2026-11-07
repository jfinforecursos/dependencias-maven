package com.example.service;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.model.NominatimResultado;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class NominatimService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public NominatimService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(6))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    public List<NominatimResultado> buscar(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) {
            return Collections.emptyList();
        }

        try {
            String url = "https://nominatim.openstreetmap.org/search?format=json&limit=5&q="
                    + URLEncoder.encode(direccion.trim(), StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    // Nominatim Usage Policy exige un User-Agent identificable
                    .header("User-Agent", "Backend1-Inventario-App/1.0 (contacto@ejemplo.com)")
                    .header("Accept", "application/json")
                    .timeout(Duration.ofSeconds(10))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), new TypeReference<List<NominatimResultado>>() {});
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}

