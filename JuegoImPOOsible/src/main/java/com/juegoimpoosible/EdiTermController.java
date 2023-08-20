package com.juegoimpoosible;

import com.juegoimpoosible.funcionalidad.Archivar;
import com.juegoimpoosible.modelo.Termino;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EdiTermController implements Initializable {

    @FXML
    private ChoiceBox<Termino> termChoiceBox; // Cambia el tipo a Termino

    @FXML
    private TextField periodoTextField;
    @FXML
    private TextField anioTextField;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Termino> terms = Archivar.readTerms();
        termChoiceBox.getItems().addAll(terms); // Agrega los términos al ChoiceBox
    }

}
