package com.juegoimpoosible;

import com.juegoimpoosible.funcionalidad.Juego;
import com.juegoimpoosible.modelo.Termino;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

import static com.juegoimpoosible.funcionalidad.Archivar.readTerms;

public class ConfTermJuegoController {

    @FXML
    private ListView<Termino> termListView;
    private Stage stage; // Referencia a la ventana actual


    public void initialize() {
        // Esta es una posible ubicación para cargar los términos al inicio
        ArrayList<Termino> terminos = readTerms(); // Llama a tu método de lectura de términos

        // Convierte la lista de términos en un ObservableList para usar con ListView
        ObservableList<Termino> observableTerminos = FXCollections.observableArrayList(terminos);

        // Configura el ListView para mostrar los términos
        termListView.setItems(observableTerminos);

        termListView.setOnMouseClicked(this::onTermSelected);


    }
    private void onTermSelected(MouseEvent event) {
        Termino selectedTerm = termListView.getSelectionModel().getSelectedItem();

        if (selectedTerm != null) {
            Juego.setTermino(selectedTerm); // Llama al método setTermino de la clase Juego
            showAlert("El término ha sido seleccionado correctamente.");
        }
    }

    // Método para mostrar una alerta
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Selección de Término");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.setOnHidden(event -> goBackToPreviousWindow());
        alert.showAndWait();
    }
    private void goBackToPreviousWindow() {
        try {
            App.setRoot("adTerm");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}
