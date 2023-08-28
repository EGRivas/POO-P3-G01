/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.juegoimpoosible;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.juegoimpoosible.funcionalidad.Juego;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
        if (Juego.getTermino() == null) {
            showAlert(Alert.AlertType.ERROR, "Error de Configuración", "Antes debes configurar el término para este juego desde la pestaña de configuraciones.");
            return; // Detener la operación
        }

        App.setRoot("confJuego");
    }
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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
