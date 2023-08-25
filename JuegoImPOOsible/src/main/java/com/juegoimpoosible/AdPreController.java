package com.juegoimpoosible;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class AdPreController {

    @FXML
    private Button backButton; // El botón de regresar

    // Método para manejar el evento de clic en el botón de regresar
    @FXML
    private void goBackToConfig(MouseEvent event) {
        try {
            App.setRoot("configuracion"); // Cambiar a la vista "configuracion.fxml"
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
