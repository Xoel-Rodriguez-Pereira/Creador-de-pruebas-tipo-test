package org.autotest.repository;

import java.io.IOException;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

public class SerializarPreguntasTest {
    SerializarPreguntas sp = new SerializarPreguntas();
    @Test
    public void testCargarTest() {
        try {
            assertTrue(sp.cargarTest("/data/preguntas.json") != null);
        } catch (IOException ex) {
            System.getLogger(SerializarPreguntasTest.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }
}
