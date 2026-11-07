package com.example.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.example.model.NominatimResultado;

public class NominatimServiceTest {

    @Test
    void testBuscarVacio() {
        NominatimService service = new NominatimService();
        List<NominatimResultado> res = service.buscar("");
        assertNotNull(res);
        assertTrue(res.isEmpty());
    }

    @Test
    void testBuscarLugarValido() {
        NominatimService service = new NominatimService();
        List<NominatimResultado> res = service.buscar("Bogota");
        assertNotNull(res);
        if (!res.isEmpty()) {
            NominatimResultado primerResultado = res.get(0);
            assertNotNull(primerResultado.getDisplayName());
            assertNotNull(primerResultado.getLat());
            assertNotNull(primerResultado.getLon());
        }
    }
}

