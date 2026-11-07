package com.example.service;

import java.io.ByteArrayInputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.model.Usuario;

public class UsuarioPdfServiceTest {

    @Test
    void testGenerarReporteConListaVacia() {
        UsuarioPdfService service = new UsuarioPdfService();
        ByteArrayInputStream pdfStream = service.generarReporteUsuarios(List.of());

        assertNotNull(pdfStream);
        assertTrue(pdfStream.available() > 0, "El PDF generado debe contener bytes");
    }

    @Test
    void testGenerarReporteConUsuarios() {
        UsuarioPdfService service = new UsuarioPdfService();
        Usuario u1 = new Usuario(1L, "Juan", "Perez", "juan@test.com", "12345678", "Calle 1 # 2-3", "admin", "activo", LocalDateTime.now());
        Usuario u2 = new Usuario(2L, "Maria", "Gomez", "maria@test.com", "87654321", "Av. Central 45", "empleado", "activo", LocalDateTime.now());

        ByteArrayInputStream pdfStream = service.generarReporteUsuarios(List.of(u1, u2));

        assertNotNull(pdfStream);
        byte[] bytes = pdfStream.readAllBytes();
        assertTrue(bytes.length > 500, "El PDF debe tener un tamaño válido");
        // Verifica que empiece con el encabezado %PDF
        assertEquals('%', (char) bytes[0]);
        assertEquals('P', (char) bytes[1]);
        assertEquals('D', (char) bytes[2]);
        assertEquals('F', (char) bytes[3]);
    }
}

