package org.autotest.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Test {
    @JsonProperty("preguntas")
    private List<Pregunta> preguntas;

    public List<Pregunta> getTest() {
        return getPreguntas();
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
}
