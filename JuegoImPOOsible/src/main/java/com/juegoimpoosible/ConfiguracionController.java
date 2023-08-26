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
        App.setRoot("menu");
    }
    @FXML
    private void goToAdTerm(MouseEvent event) throws IOException {
        App.setRoot("adTerm");
    }

    @FXML
    private void goToAdSub(MouseEvent event) throws IOException {
        App.setRoot("adMateria");
    }
    @FXML
    private void goToAdPre(MouseEvent event) throws IOException {
        App.setRoot("adPre");
    }

}