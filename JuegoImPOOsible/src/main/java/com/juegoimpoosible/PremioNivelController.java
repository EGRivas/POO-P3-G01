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
public class PremioNivelController implements Initializable {

    @FXML
    private TextField lblPremio;
    @FXML
    private Button buttonBack;
    @FXML
    private AnchorPane paneAnc;
    @FXML
    private Label labelConsuelo;
    @FXML
    private Label labelLost;


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        paneAnc.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        paneAnc.getStyleClass().add("fondo");
        buttonBack.getStyleClass().add("menu-buttons");
        labelConsuelo.getStyleClass().add("labels");
        labelLost.getStyleClass().add("labels");
        lblPremio.getStyleClass().add("text-fields");
        
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
