package com.juegoimpoosible;

import com.juegoimpoosible.funcionalidad.Archivar;
import com.juegoimpoosible.modelo.Termino;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

import java.io.IOException;
import java.util.ArrayList;

public class IngTermController {

    @FXML
    private TextField periodoTextField;
    @FXML
    private TextField anioTextField;

    @FXML
    private void goBackToAdTerm() throws IOException {
        App.setRoot("adTerm");
    }

    @FXML
    private void saveTerm() {
        String periodo = periodoTextField.getText();
        String anio = anioTextField.getText();

        if (periodo.isEmpty() || anio.isEmpty()) {
            showAlert("Error", "Por favor, ingrese el periodo y el año.");
            return;
        }
        Termino newTerm = new Termino(anio, periodo);
        // Verifica si el término ya existe
        ArrayList<Termino> terms = Archivar.readTerms();
        if (terms.contains(newTerm)) {
            showAlert("Error", "El término ingresado ya existe.");
            return;
        }


        // Verifica si el año es válido
        int currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);
        int inputYear = Integer.parseInt(anio);
        if (inputYear < currentYear) {
            showAlert("Error", "El año ingresado es menor al año actual.");
            return;
        }

        // Si pasó todas las validaciones, crea el término y guárdalo
        terms.add(newTerm);
        Archivar.writeTerms(terms);

        // Muestra una alerta de éxito
        showAlert("Éxito", "Término guardado correctamente.");
    }

    // Método para mostrar una alerta
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
