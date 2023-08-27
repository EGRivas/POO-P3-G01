package com.juegoimpoosible;

import com.juegoimpoosible.funcionalidad.Archivar;
import com.juegoimpoosible.modelo.Materia;
import com.juegoimpoosible.modelo.Preguntas;
import com.juegoimpoosible.modelo.Termino;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class AdPreController implements Initializable {

    @FXML
    private Button backButton; // El botón de regresar
    @FXML
    private ComboBox<Materia> materiaComboBox;
    @FXML
    private ListView<String> enun;
    @FXML
    private ListView<Integer> nivel;
    @FXML
    private ListView<String> respCorrec;
    @FXML
    private ListView<String> resp1;
    @FXML
    private ListView<String> resp2;
    @FXML
    private ListView<String> resp3;
    @FXML
    private ListView<String> opciones;
    @FXML
    private TextField enunN;
    @FXML
    private TextField nivelN;
    @FXML
    private TextField respCorrecN;
    @FXML
    private TextField resp1N;
    @FXML
    private TextField resp2N;
    @FXML
    private TextField resp3N;

    private Materia selectedMateria;
    private static ArrayList<Termino> listTerm;
    private ArrayList<Materia> materias;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Aquí cargamos las materias en el ComboBox
        listTerm = Archivar.readTerms();
        materias = new ArrayList<>();
        for(Termino t: listTerm){
            materias.addAll(t.getMaterias());
        }
        materiaComboBox.getItems().addAll(materias);
        opciones.setOnMouseClicked(this::handleOptionClick);

    }

    @FXML
    private void goBackToConfig(MouseEvent event) {
        try {
            App.setRoot("configuracion"); // Cambiar a la vista "configuracion.fxml"
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void guardarPregunta(MouseEvent event) {
        if (selectedMateria == null) {
            // Mostrar una alerta indicando que no se ha seleccionado una materia
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Materia no seleccionada");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, seleccione una materia antes de guardar la pregunta.");
            alert.showAndWait();
        } else {
            if (enunN.getText().isEmpty() || nivelN.getText().isEmpty() || respCorrecN.getText().isEmpty() ||
                    resp1N.getText().isEmpty() || resp2N.getText().isEmpty() || resp3N.getText().isEmpty()) {
                // Mostrar una alerta si algún campo está vacío
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Campos vacíos");
                alert.setHeaderText(null);
                alert.setContentText("Por favor, llene todos los campos antes de guardar la pregunta.");
                alert.showAndWait();
            } else {
                // Crear una nueva pregunta y agregarla a la lista de preguntas de la materia seleccionada
                ArrayList<String> respuestas = new ArrayList<>();
                respuestas.add(resp1N.getText());
                respuestas.add(resp2N.getText());
                respuestas.add(resp3N.getText());
                respuestas.add(respCorrecN.getText()); // La respuesta correcta se agrega al final

                Preguntas nuevaPregunta = new Preguntas(enunN.getText(), Integer.parseInt(nivelN.getText()), respuestas, respCorrecN.getText());
                selectedMateria.agregarPregunta(nuevaPregunta);
                ArrayList<Materia> materias = Archivar.readSubjects();
                for (int i = 0; i < materias.size(); i++) {
                    if (materias.get(i).getCodigo().equals(selectedMateria.getCodigo())) {
                        materias.set(i, selectedMateria);
                        break;
                    }
                }
                for (Termino termino : listTerm) {
                    if (termino.getMaterias().contains(selectedMateria)) {
                        // Actualizar la lista de materias en el término
                        termino.setMaterias(materias);
                        break;
                    }
                }

// Escribir los términos actualizados al archivo serial
                Archivar.writeTerms(listTerm);

                // Mostrar una alerta de éxito
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Pregunta guardada");
                alert.setHeaderText(null);
                alert.setContentText("La pregunta se ha guardado exitosamente en la materia: " + selectedMateria.getNombre());
                alert.showAndWait();

                // Limpiar los campos después de guardar
                enunN.clear();
                nivelN.clear();
                respCorrecN.clear();
                resp1N.clear();
                resp2N.clear();
                resp3N.clear();
            }
        }
    }
    @FXML
    private void handleMateriaSelection(MouseEvent event) {
        selectedMateria = materiaComboBox.getSelectionModel().getSelectedItem();
        if (selectedMateria != null) {
            enun.getItems().clear();
            nivel.getItems().clear();
            respCorrec.getItems().clear();
            resp1.getItems().clear();
            resp2.getItems().clear();
            resp3.getItems().clear();
            opciones.getItems().clear();

            // Ordenar las preguntas según el nivel
            ArrayList<Preguntas> preguntasOrdenadas = new ArrayList<>(selectedMateria.getPreguntas());
            preguntasOrdenadas.sort(Comparator.comparingInt(Preguntas::getNivel));

            for (Preguntas pregunta : preguntasOrdenadas) {
                enun.getItems().add(pregunta.getEnunciado());
                nivel.getItems().add(pregunta.getNivel());
                respCorrec.getItems().add(pregunta.getRespuestaCorrecta());
                ArrayList<String> respuestas = pregunta.listaRespuestas();
                resp1.getItems().add(respuestas.get(0));
                resp2.getItems().add(respuestas.get(1));
                resp3.getItems().add(respuestas.get(2));
                opciones.getItems().add("Eliminar");
            }
        }
    }

    @FXML
    private void handleOptionClick(MouseEvent event) {
        if (event.getClickCount() == 1) {
            int selectedIndex = opciones.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                // Eliminar la pregunta correspondiente en la misma fila
                Preguntas preguntaAEliminar = selectedMateria.getPreguntas().get(selectedIndex);
                selectedMateria.eliminarPregunta(preguntaAEliminar);

                // Actualizar la vista de las listas
                updateListViewItems();

                // Sobrescribir la lista de materias en el archivo serial
                ArrayList<Materia> materias = Archivar.readSubjects();
                for (int i = 0; i < materias.size(); i++) {
                    if (materias.get(i).getCodigo().equals(selectedMateria.getCodigo())) {
                        materias.set(i, selectedMateria);
                        break;
                    }
                }
                for (Termino termino : listTerm) {
                    if (termino.getMaterias().contains(selectedMateria)) {
                        // Actualizar la lista de materias en el término
                        termino.setMaterias(materias);
                        break;
                    }
                }

// Escribir los términos actualizados al archivo serial
                Archivar.writeTerms(listTerm);
            }
        }
    }

    private void updateListViewItems() {
        enun.getItems().clear();
        nivel.getItems().clear();
        respCorrec.getItems().clear();
        resp1.getItems().clear();
        resp2.getItems().clear();
        resp3.getItems().clear();
        opciones.getItems().clear();

        for (Preguntas pregunta : selectedMateria.getPreguntas()) {
            enun.getItems().add(pregunta.getEnunciado());
            nivel.getItems().add(pregunta.getNivel());
            respCorrec.getItems().add(pregunta.getRespuestaCorrecta());
            ArrayList<String> respuestas = pregunta.listaRespuestas();
            resp1.getItems().add(respuestas.get(0));
            resp2.getItems().add(respuestas.get(1));
            resp3.getItems().add(respuestas.get(2));
            opciones.getItems().add("Eliminar");
        }
    }

}
