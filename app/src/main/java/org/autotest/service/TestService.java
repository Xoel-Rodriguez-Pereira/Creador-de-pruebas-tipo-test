package org.autotest.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import org.autotest.model.Pregunta;
import org.autotest.model.Solucion;

public class TestService {
    private static final int PREGUNTAS_POR_BATERIA = 20;

    public List<Pregunta> seleccionarPreguntas(List<Pregunta> preguntas) {
        Objects.requireNonNull(preguntas, "La lista de preguntas no puede ser null");

        List<Pregunta> disponibles = new ArrayList<>(preguntas);
        Collections.shuffle(disponibles);
        return new ArrayList<>(disponibles.subList(0, Math.min(PREGUNTAS_POR_BATERIA, disponibles.size())));
    }

    public Resultado corregir(List<Pregunta> preguntas, List<Solucion> respuestas) {
        Objects.requireNonNull(preguntas, "La lista de preguntas no puede ser null");
        Objects.requireNonNull(respuestas, "La lista de respuestas no puede ser null");
        if (preguntas.size() != respuestas.size()) {
            throw new IllegalArgumentException("Debe haber una respuesta por cada pregunta");
        }

        int aciertos = 0;
        int fallos = 0;
        int sinResponder = 0;
        for (int i = 0; i < preguntas.size(); i++) {
            Pregunta pregunta = Objects.requireNonNull(preguntas.get(i), "La pregunta no puede ser null");
            Solucion respuesta = respuestas.get(i);

            if (respuesta == null) {
                sinResponder++;
            } else if (respuesta == pregunta.getSolucion()) {
                aciertos++;
            } else {
                fallos++;
            }
        }

        double nota = preguntas.isEmpty()
                ? 0
                : Math.max(0, (aciertos - fallos * 0.5) * 10.0 / preguntas.size());
        return new Resultado(aciertos, fallos, sinResponder, nota);
    }

    public record Resultado(int aciertos, int fallos, int sinResponder, double nota) {
    }
}
