package org.autotest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Pregunta {
    @JsonProperty("enunciado")
    private String enunciado;
    
    @JsonProperty("solucion")
    private Solucion solucion;

    @JsonProperty("opciones")
    private String[] opciones;

    public String getEnunciado() { return enunciado; }
    public void setEnunciado(String value) { this.enunciado = value; }

    public Solucion getSolucion() { return solucion; }
    public void setSolucion(Solucion value) { this.solucion = value; }

    public String[] getOpciones() { return opciones; }
    public void setOpciones(String[] value) { this.opciones = value; }
}
