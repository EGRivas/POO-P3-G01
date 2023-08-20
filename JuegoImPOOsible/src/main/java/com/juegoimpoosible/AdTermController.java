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
        ArrayList<Termino> terms = Archivar.readTerms();
        termListView.getItems().addAll(terms);
    }
    @FXML
    private void goBack() throws IOException, IOException {
        App.setRoot("configuracion"); // Cambiar a la vista de configuración
    }
}