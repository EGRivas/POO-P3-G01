/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.juegoimpoosible;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * FXML Controller class
 *
 * @author administrador
 */
public class JuegoViewController implements Initializable {

    @FXML
    private Label lblTiempo;
    @FXML
    private Label lblPregunta;
    @FXML
    private GridPane contRespuestas;
    @FXML
    private Button botonA;
    @FXML
    private Button botonB;
    @FXML
    private Button botonC;
    @FXML
    private Button botonD;
    @FXML
    private Label lblComodines;
    @FXML
    private HBox contComodines;
    @FXML
    private VBox contAvancePreguntas;
    @FXML
    private BorderPane borderPane;
    private ImageView img50;
    private ImageView imgComp;
    private ImageView imgCurso;
    
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contComodines.getChildren().clear();
        try {
            img50 = new ImageView(new Image(new FileInputStream("JuegoImPOOsible/src/main/java/imagenes/50.png"),50, 50,false,false));
            imgComp = new ImageView(new Image(new FileInputStream("JuegoImPOOsible/src/main/java/imagenes/comp.png"),50, 50,false,false));
            imgCurso = new ImageView(new Image(new FileInputStream("JuegoImPOOsible/src/main/java/imagenes/curso.png"),50, 50,false,false));
            
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        contComodines.getChildren().add(img50);
        contComodines.getChildren().add(imgComp);
        contComodines.getChildren().add(imgCurso);
        
        contComodines.setAlignment(Pos.TOP_CENTER);
    }
    
}
