package org.autotest.controller;

import java.util.List;
import java.util.Objects;

import javafx.fxml.FXML;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.autotest.model.Pregunta;
import org.autotest.model.Solucion;

public class PreguntaController {
    private static final List<String> REVIEW_STYLES =
            List.of("correct-answer", "incorrect-answer", "unanswered");

    @FXML
    private Label enunciadoLabel;

    @FXML
    private VBox opcionesContainer;

    @FXML
    private VBox preguntaCard;

    private final ToggleGroup opcionesGroup = new ToggleGroup();
    private final RadioButton[] botonesOpciones = new RadioButton[Solucion.values().length];
    private final Label[] iconosRevision = new Label[Solucion.values().length];

    public void setPregunta(Pregunta pregunta) {
        Objects.requireNonNull(pregunta, "La pregunta no puede ser null");
        String enunciado = Objects.requireNonNull(pregunta.getEnunciado(),
                "La pregunta debe tener un enunciado");
        String[] opciones = Objects.requireNonNull(pregunta.getOpciones(),
                "La pregunta debe tener opciones");
        if (enunciado.isBlank()) {
            throw new IllegalArgumentException("El enunciado de la pregunta no puede estar vacío");
        }
        if (opciones.length != Solucion.values().length) {
            throw new IllegalArgumentException("Cada pregunta debe tener exactamente cuatro opciones");
        }
        Objects.requireNonNull(pregunta.getSolucion(),
                "La pregunta debe tener una solución correcta");

        enunciadoLabel.setText(enunciado);
        enunciadoLabel.setMaxWidth(Double.MAX_VALUE);
        preguntaCard.setMaxWidth(Double.MAX_VALUE);
        for (int i = 0; i < opciones.length; i++) {
            String textoOpcion = Objects.requireNonNull(opciones[i], "Las opciones no pueden ser null");
            if (textoOpcion.isBlank()) {
                throw new IllegalArgumentException("Las opciones no pueden estar vacías");
            }
            RadioButton opcion = new RadioButton(textoOpcion);
            opcion.setWrapText(true);
            opcion.setMaxWidth(Double.MAX_VALUE);
            opcion.getStyleClass().add("answer-option");
            opcion.setUserData(Solucion.values()[i]);
            opcion.setToggleGroup(opcionesGroup);
            botonesOpciones[i] = opcion;

            Label icono = new Label();
            icono.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            icono.setMinWidth(22.0);
            icono.getStyleClass().add("review-icon");
            iconosRevision[i] = icono;

            HBox fila = new HBox(10.0, opcion, icono);
            fila.setAlignment(javafx.geometry.Pos.CENTER_LEFT);
            HBox.setHgrow(opcion, javafx.scene.layout.Priority.ALWAYS);
            opcionesContainer.getChildren().add(fila);
        }
    }

    public Solucion getRespuestaSeleccionada() {
        if (opcionesGroup.getSelectedToggle() == null) {
            return null;
        }
        return (Solucion) opcionesGroup.getSelectedToggle().getUserData();
    }

    public void mostrarCorreccion(Solucion respuesta, Solucion correcta) {
        boolean sinResponder = respuesta == null;
        boolean acertada = respuesta == correcta;

        preguntaCard.getStyleClass().removeAll(REVIEW_STYLES);
        if (acertada) {
            preguntaCard.getStyleClass().add("correct-answer");
        } else if (sinResponder) {
            preguntaCard.getStyleClass().add("unanswered");
        } else {
            preguntaCard.getStyleClass().add("incorrect-answer");
        }

        for (int i = 0; i < botonesOpciones.length; i++) {
            Solucion opcion = Solucion.values()[i];
            botonesOpciones[i].setDisable(true);
            if (opcion == correcta) {
                iconosRevision[i].setGraphic(crearIcono("/img/tick.png"));
            } else if (opcion == respuesta) {
                iconosRevision[i].setGraphic(crearIcono("/img/cross.png"));
            }
        }
    }

    private ImageView crearIcono(String recurso) {
        var url = Objects.requireNonNull(getClass().getResource(recurso),
                "No se encontró el icono " + recurso);
        ImageView vista = new ImageView(new Image(url.toExternalForm()));
        vista.setFitWidth(20);
        vista.setFitHeight(20);
        vista.setPreserveRatio(true);
        return vista;
    }
}
