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
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Administrador
 */
public class MenuController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        panel.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        config.getStyleClass().add("menu-buttons");
        report.getStyleClass().add("menu-buttons");
        newGame.getStyleClass().add("menu-buttons");
        panel.getStyleClass().add("fondo");
        titulo.getStyleClass().add("titulo");
    }
    @FXML
    private void goToJuego(MouseEvent event) throws IOException {
        App.setRoot("juegoView");
    }
    @FXML
    private void goToConfiguracion(MouseEvent event) throws IOException {
        App.setRoot("configuracion");
    }


    @FXML
    private Button report;
    @FXML
    private Button newGame;
    @FXML
    private Button config;
    @FXML
    private BorderPane panel;
    @FXML
    private Text titulo;





}