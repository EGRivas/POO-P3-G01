/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.juegoimpoosible;

import com.juegoimpoosible.modelo.*;
import com.juegoimpoosible.funcionalidad.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    
    
    static Juego juego;
    private static Estudiante estPart;
    private static Estudiante estApoyo;
    private static ArrayList<Preguntas> contPreguntas;
    private static Preguntas currentPreg;
    private static ArrayList<String> listAnswers; 
    private String respCorrecta;
    private ArrayList<TipoComodin> comodines;
    
    private ArrayList<String> presentacionPreg;
    private ArrayList<Button> listaBotones;
    //seguimiento de preguntas
    private int cont;
    //nivel alcanzado
    private int alcanzado = 0;
    @FXML
    private ListView<String> questionView;
    
    
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //esto solo ocurre una vez 
        comodines = new ArrayList<>();
        cont = 0;
        iniciarRecursos();
        juego = ConfJuegoController.juegoIn;
        juego.setNivelAlcanzado(alcanzado);
        estPart = ConfJuegoController.participante;
        estApoyo = ConfJuegoController.apoyo;
        ArrayList<Preguntas> preguntasTotales = ConfJuegoController.preguntas;
        //baraja de las preguntas
        Collections.shuffle(preguntasTotales);
        contPreguntas = new ArrayList<>();
        //separacion de preguntas por nivel
        for(int x = 1;x<=juego.getNivelMax();x++){
            int conteoPreg = 0;
            for(Preguntas p : preguntasTotales){
                if(conteoPreg < juego.getNumPreNivel() && p.getNivel() == x){
                    conteoPreg++;
                    contPreguntas.add(p);
                }
            }
        }
        
        Collections.shuffle(contPreguntas);
        presentacionPreg = new ArrayList<>();
        for(int x=1;x<=contPreguntas.size();x++){
            presentacionPreg.add("Pregunta "+x);
        }
        questionView.getItems().addAll(presentacionPreg);
        
        //proceso a repetir
        currentPreg = contPreguntas.get(cont);
        lblPregunta.setText(currentPreg.getEnunciado());
        
        listAnswers = currentPreg.listaRespuestas();
        Collections.shuffle(listAnswers);
        botonA.setText("A: "+listAnswers.get(0));
        botonB.setText("B: "+listAnswers.get(1));
        botonC.setText("C: "+listAnswers.get(2));
        botonD.setText("D: "+listAnswers.get(3));
        
        respCorrecta = currentPreg.getRespuestaCorrecta();
        
        img50.setOnMouseClicked(e -> comodin5050());
        imgComp.setOnMouseClicked(e -> comodinApoyo());
        imgCurso.setOnMouseClicked(e -> comodinClase());
        
    }
    
    public void iniciarRecursos(){
        contComodines.getChildren().clear();
        try {
            img50 = new ImageView(new Image(new FileInputStream("JuegoImPOOsible/imagenes/50.png"),50, 50,false,false));
            imgComp = new ImageView(new Image(new FileInputStream("JuegoImPOOsible/imagenes/comp.png"),50, 50,false,false));
            imgCurso = new ImageView(new Image(new FileInputStream("JuegoImPOOsible/imagenes/curso.png"),50, 50,false,false));
        } catch (FileNotFoundException ex) {
            System.out.println(ex.getMessage());
            try{
                img50 = new ImageView(new Image(new FileInputStream("imagenes/50.png"),50, 50,false,false));
                imgComp = new ImageView(new Image(new FileInputStream("imagenes/comp.png"),50, 50,false,false));
                imgCurso = new ImageView(new Image(new FileInputStream("imagenes/curso.png"),50, 50,false,false));
            } catch(FileNotFoundException e){
                System.out.println(e.getMessage());
            }
        }
        
        img50.getStyleClass().add("comodin");
        imgComp.getStyleClass().add("comodin");
        imgCurso.getStyleClass().add("comodin");
        contComodines.getChildren().add(img50);
        contComodines.getChildren().add(imgComp);
        contComodines.getChildren().add(imgCurso);
        contComodines.setAlignment(Pos.TOP_CENTER);
        borderPane.getStylesheets().add(getClass().getResource("Styles.css").toExternalForm());
        botonA.getStyleClass().add("button-styled");
        botonB.getStyleClass().add("button-styled");
        botonC.getStyleClass().add("button-styled");
        botonD.getStyleClass().add("button-styled");
    }
    
    @FXML
    public void accionBotonA(){
        if(botonA.getText().equals("A: "+respCorrecta)){
            seguimiento();
        } else{
            System.out.println("mal");
            juegoTerminado();
        }
    }
    @FXML
    public void accionBotonB(){
        if(botonB.getText().equals("B: "+respCorrecta)){
            seguimiento();
        } else{
            System.out.println("mal");
            juegoTerminado();
        }
    }
    @FXML
    public void accionBotonC(){
        if(botonC.getText().equals("C: "+respCorrecta)){
            seguimiento();
        } else{
            System.out.println("mal");
            juegoTerminado();
        }
    }
    @FXML
    public void accionBotonD(){
        if(botonD.getText().equals("D: "+respCorrecta)){
            seguimiento();
        } else{
            System.out.println("mal");
            juegoTerminado();
        }
    }
    
    
    public void seguimiento(){
        botonA.setDisable(false);
        botonB.setDisable(false);
        botonC.setDisable(false);
        botonD.setDisable(false);
        cont++;
        if(cont<contPreguntas.size()){
            currentPreg = contPreguntas.get(cont);
            lblPregunta.setText(currentPreg.getEnunciado());
            
            listAnswers = currentPreg.listaRespuestas();
            Collections.shuffle(listAnswers);
            botonA.setText("A: "+listAnswers.get(0));
            botonB.setText("B: "+listAnswers.get(1));
            botonC.setText("C: "+listAnswers.get(2));
            botonD.setText("D: "+listAnswers.get(3));
            
            respCorrecta = currentPreg.getRespuestaCorrecta();
            if(cont%juego.getNumPreNivel() == 0){
                alcanzado++;
                juego.setNivelAlcanzado(alcanzado);
            }
            
        } else{
            //has ganadoooo
            alcanzado++;
            juegoTerminado();
        }
    }
    public void juegoTerminado(){
        System.out.println("");
        //conteo de los niveles alcanzados
        if(alcanzado > 0 && alcanzado<juego.getNivelMax()){
            //premio de consolacion
            try{
                goToPremioNivel();
            }catch(IOException e){
                e.printStackTrace();
            }
        } else if(alcanzado >= juego.getNivelMax()){
            //has ganado
            try{
                goToPremioCompleto();
            }catch(IOException e){
                e.printStackTrace();
            }
        } else {
            //has perdido xd
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("¡Has perdido!");
            alert.setHeaderText("Recuerda que puedes volver a participar :)");
            alert.setContentText("Da click en el botón para regresar al menu");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                try{
                    goBack();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
    
    public void comodin5050(){
        if(comodines.contains(TipoComodin.COMODIN5050) == false){
            //boton a -> 0
            //boton b -> 1
            //boton c -> 2
            //boton d -> 3
            
            int index = listAnswers.indexOf(currentPreg.getRespuestaCorrecta());
            System.out.println(index);
            
            int borrar1;
            int borrar2;
            do{
                borrar1 = (int)(Math.random()*4);
                borrar2 = (int)(Math.random()*4);
            }while(borrar1 == index || borrar2 == index || borrar1 == borrar2);
            System.out.println(borrar1+" "+borrar2);
            if(borrar1==0 || borrar2==0){
                botonA.setDisable(true);
            }
            if(borrar1==1 || borrar2==1){
                botonB.setDisable(true);
            }
            if(borrar1==2 || borrar2==2){
                botonC.setDisable(true);
            }
            if(borrar1==3 || borrar2==3){
                botonD.setDisable(true);
            }
            comodines.add(TipoComodin.COMODIN5050);
        }
    }
    public void comodinApoyo(){
        if(comodines.contains(TipoComodin.COMODINCOMPANERO)==false) comodines.add(TipoComodin.COMODINCOMPANERO);
    }
    public void comodinClase(){
        if(comodines.contains(TipoComodin.COMODINCLASE)==false) comodines.add(TipoComodin.COMODINCLASE);
    }
    private void goBack() throws IOException {
        App.setRoot("menu");
    }
    private void goToPremioCompleto() throws IOException {
        App.setRoot("premioCompleto");
    }
    private void goToPremioNivel() throws IOException {
        App.setRoot("premioNivel");
    }
    
    //thread de los segundos
    /*public void timer(){
        Thread th = new Thread(()->{
            for(int i = 0;i<60;i++){
                Platform.runLater(()->lblTiempo.setText(i+"s"));
                try{
                    Thread.sleep(2000);
                } catch(InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
        th.start();
    }*/
}
