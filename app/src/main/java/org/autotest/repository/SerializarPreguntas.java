package org.autotest.repository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.autotest.model.Test;

import tools.jackson.databind.ObjectMapper;

public class SerializarPreguntas {
    private final ObjectMapper mapper = new ObjectMapper();

    public Test cargarTest(String archivo) throws IOException {
        InputStream recurso = getClass().getResourceAsStream(archivo);
        if (recurso == null) {
            throw new FileNotFoundException("No se encontró el recurso /data/preguntas.json");
        }

        try (recurso) {
            return mapper.readValue(recurso, Test.class);
        }
    }
}
