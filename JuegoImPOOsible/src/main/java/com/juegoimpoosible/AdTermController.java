package com.juegoimpoosible;

import com.juegoimpoosible.funcionalidad.Archivar;
import com.juegoimpoosible.modelo.Termino;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdTermController implements Initializable {

    @FXML
    private ListView<Termino> termListView;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            ArrayList<Termino> terms = Archivar.readTerms();
            termListView.getItems().addAll(terms);
        } catch(Exception e) {
            System.out.println("[-]No se a creado el archivo serializado");
        }
    }
    @FXML
    private void goBack() throws IOException, IOException {
        App.setRoot("configuracion");
    }
    @FXML
    private void goToIngTerm() throws IOException {
        App.setRoot("ingTerm");
    }
    @FXML
    private void goToEdiTerm() throws IOException {
        App.setRoot("ediTerm");
    }
    @FXML
    private void goToConfTermJuego() throws IOException {
        App.setRoot("confTermJuego");
    }




}