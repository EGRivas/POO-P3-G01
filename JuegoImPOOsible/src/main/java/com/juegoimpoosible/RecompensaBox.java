/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.juegoimpoosible;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.control.Label;

/**
 *
 * @author Administrador
 */
public class RecompensaBox {
    
    public static String display(String title, String message){
        String recompensa;
        Stage window = new Stage();
        
        //inicio de ventana de alerta
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        
        Label label = new Label(message);
        label.setText(message);
        
        //textfield donde se ingresa el premio
        TextField premio = new TextField();
        
        //retenedor del texto ingresado para la expresion lambda
        Label retenedor = new Label();
        // cuando la tecla espacio sea presionada
        premio.setOnAction(e -> retenedor.setText(premio.getText()));
        recompensa = retenedor.getText();
        
        //contenedor principal
        VBox layout = new VBox(30);
        layout.getChildren().addAll(label,premio);
        layout.setAlignment(Pos.CENTER);
        
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();
        
        
        return recompensa;
    }
}
