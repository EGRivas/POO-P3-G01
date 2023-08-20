package com.juegoimpoosible;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;

public class ConfiguracionController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    @FXML
    private void goBack(MouseEvent event) throws IOException {
        App.setRoot("menu"); // Cambia "menu" por el nombre del archivo FXML del menú
    }
}