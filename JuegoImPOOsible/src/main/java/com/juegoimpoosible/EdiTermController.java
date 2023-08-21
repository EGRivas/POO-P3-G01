package com.juegoimpoosible;

import com.juegoimpoosible.funcionalidad.Archivar;
import com.juegoimpoosible.modelo.Termino;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import com.juegoimpoosible.funcionalidad.Archivar;
import com.juegoimpoosible.modelo.Termino;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EdiTermController implements Initializable {


    public ListView termListView;
    @FXML
    private TextField periodoTextField;
    @FXML
    private TextField anioTextField;
    @FXML
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Termino> terms = Archivar.readTerms();
        termListView.getItems().addAll(terms);

        termListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                periodoTextField.setText(((Termino)newValue).getPeriodo());
                anioTextField.setText(((Termino)newValue).getAnio());
            }
        });
    }

    @FXML
    private void handleGuardarButton() {
        Termino selectedTerm = (Termino) termListView.getSelectionModel().getSelectedItem();
        if (selectedTerm != null) {
            selectedTerm.setPeriodo(periodoTextField.getText());
            selectedTerm.setAnio(anioTextField.getText());
            termListView.refresh(); // Actualizar la vista del ListView
        }
    }


}
