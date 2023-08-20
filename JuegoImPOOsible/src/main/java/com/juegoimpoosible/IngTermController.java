package com.juegoimpoosible;

import javafx.fxml.FXML;

import java.io.IOException;

public class IngTermController {
    @FXML
    private void goBackToAdTerm() throws IOException {
        App.setRoot("adTerm");
    }
}
