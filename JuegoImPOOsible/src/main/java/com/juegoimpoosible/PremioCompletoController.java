/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.juegoimpoosible;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Administrador
 */
public class PremioCompletoController implements Initializable {

    @FXML
    private TextField lblPremio;
    @FXML
    private Button buttonBack;
    @FXML
    private Label labelRecompensa;
    @FXML
    private Label labelGanaste;
    @FXML
    private AnchorPane paneAnc;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paneAnc.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        paneAnc.getStyleClass().add("fondo");
        labelGanaste.getStyleClass().add("labels");
        labelRecompensa.getStyleClass().add("labels");
        lblPremio.getStyleClass().add("text-fields");
        buttonBack.getStyleClass().add("menu-buttons");
        
    }
    @FXML
    private void goBack() throws IOException {
        App.setRoot("menu");
    }
    @FXML
    private void premio(){
        JuegoViewController.juego.setPremioGanado(lblPremio.getText());
    }
}
