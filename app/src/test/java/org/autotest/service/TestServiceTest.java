package org.autotest.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.autotest.model.Pregunta;
import org.autotest.model.Solucion;
import org.junit.Test;

public class TestServiceTest {
    private final TestService testService = new TestService();

    @Test
    public void corregirCuentaAciertosFallosYNoRespondidas() {
        List<Pregunta> preguntas = List.of(
                pregunta(Solucion.A),
                pregunta(Solucion.B),
                pregunta(Solucion.C));

        TestService.Resultado resultado = testService.corregir(
                preguntas, java.util.Arrays.asList(Solucion.A, Solucion.D, null));

        assertEquals(1, resultado.aciertos());
        assertEquals(1, resultado.fallos());
        assertEquals(1, resultado.sinResponder());
        assertEquals(5.0 / 3.0, resultado.nota(), 0.0001);
    }

    @Test
    public void corregirNoPermiteNotaNegativa() {
        List<Pregunta> preguntas = List.of(pregunta(Solucion.A), pregunta(Solucion.B));

        TestService.Resultado resultado = testService.corregir(
                preguntas, List.of(Solucion.C, Solucion.D));

        assertEquals(0.0, resultado.nota(), 0.0);
    }

    @Test
    public void seleccionarPreguntasNoSuperaVeinteNiRepitePreguntas() {
        List<Pregunta> preguntas = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            preguntas.add(pregunta(Solucion.A));
        }

        List<Pregunta> seleccion = testService.seleccionarPreguntas(preguntas);

        assertEquals(20, seleccion.size());
        assertEquals(20, new HashSet<>(seleccion).size());
        assertTrue(preguntas.containsAll(seleccion));
    }

    private Pregunta pregunta(Solucion solucion) {
        Pregunta pregunta = new Pregunta();
        pregunta.setSolucion(solucion);
        return pregunta;
    }
}
