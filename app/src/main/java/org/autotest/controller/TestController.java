package org.autotest.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javafx.application.Platform;
import org.autotest.model.Pregunta;
import org.autotest.model.Solucion;
import org.autotest.service.TestService;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

public class TestController {
    @FXML
    private VBox preguntasContainer;

    @FXML
    private Button comprobarButton;

    @FXML
    private Button reiniciarButton;

    @FXML
    private ScrollPane preguntasScrollPane;

    @FXML
    private Label notaLabel;

    @FXML
    private Label aciertosLabel;

    @FXML
    private Label fallosLabel;

    @FXML
    private Label sinResponderLabel;

    private final List<PreguntaController> preguntaControllers = new ArrayList<>();
    private final TestService testService = new TestService();
    private List<Pregunta> todasLasPreguntas = List.of();
    private List<Pregunta> preguntasActuales;

    public void setPreguntas(List<Pregunta> preguntas) throws IOException {
        Objects.requireNonNull(preguntas, "La lista de preguntas no puede ser null");
        todasLasPreguntas = List.copyOf(preguntas);
        crearNuevaBateria();
    }

    private void crearNuevaBateria() throws IOException {
        preguntasActuales = testService.seleccionarPreguntas(todasLasPreguntas);
        preguntaControllers.clear();
        preguntasContainer.getChildren().clear();

        for (Pregunta pregunta : preguntasActuales) {
            FXMLLoader loader = new FXMLLoader(
                    Objects.requireNonNull(getClass().getResource("/ui/pregunta.fxml"),
                            "No se encontró /ui/pregunta.fxml"));
            Node vistaPregunta = loader.load();
            PreguntaController controller = loader.getController();
            controller.setPregunta(pregunta);
            preguntaControllers.add(controller);
            preguntasContainer.getChildren().add(vistaPregunta);
        }

        reiniciarButton.setDisable(todasLasPreguntas.isEmpty());
        preguntasScrollPane.setVvalue(0);
        reiniciarPuntuacion();
    }

    @FXML
    public void comprobar() {
        if (preguntaControllers.isEmpty()) {
            return;
        }

        List<Solucion> respuestas = new ArrayList<>(preguntaControllers.size());
        for (int i = 0; i < preguntaControllers.size(); i++) {
            respuestas.add(preguntaControllers.get(i).getRespuestaSeleccionada());
            preguntaControllers.get(i).mostrarCorreccion(
                    respuestas.get(i), preguntasActuales.get(i).getSolucion());
        }

        TestService.Resultado resultado = testService.corregir(preguntasActuales, respuestas);
        notaLabel.setText(String.format(java.util.Locale.ROOT, "Nota: %.2f / 10", resultado.nota()));
        aciertosLabel.setText("Aciertos: " + resultado.aciertos());
        fallosLabel.setText("Fallos: " + resultado.fallos());
        sinResponderLabel.setText("Sin responder: " + resultado.sinResponder());
        comprobarButton.setDisable(true);
    }

    @FXML
    public void reiniciar() throws IOException {
        crearNuevaBateria();
    }

    private void reiniciarPuntuacion() {
        notaLabel.setText(preguntasActuales.isEmpty() ? "No hay preguntas disponibles" : "Nota: -- / 10");
        aciertosLabel.setText("Aciertos: 0");
        fallosLabel.setText("Fallos: 0");
        sinResponderLabel.setText("Sin responder: 0");
        comprobarButton.setDisable(preguntasActuales.isEmpty());
    }

    @FXML
    private void cerrarAplicacion() {
        Platform.exit();
    }
}
