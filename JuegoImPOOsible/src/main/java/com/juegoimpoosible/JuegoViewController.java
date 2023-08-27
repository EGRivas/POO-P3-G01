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
import javafx.scene.control.*;
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
public class JuegoViewController implements Initializable, Runnable {

    @FXML
    private Label lblTiempo;;
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
    @FXML
    private SplitPane splitted;
    private ImageView img50;
    private ImageView imgComp;
    private ImageView imgCurso;
    private static int i;
    private static boolean noVolverEjecutar = false;


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

        //esto solo ocurre una vez
        comodines = new ArrayList<>();
        lblTiempo.setText("60s");
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
        timer();
        respCorrecta = currentPreg.getRespuestaCorrecta();

        img50.setOnMouseClicked(e -> comodin5050());
        imgComp.setOnMouseClicked(e -> comodinApoyo());
        imgCurso.setOnMouseClicked(e -> comodinClase());



    }

    @Override
    public void run(){
        for(i = 60; i >= 0; i--){

            if(lblTiempo != null){
                Platform.runLater(() -> lblTiempo.setText(i + "s"));
                System.out.println(lblTiempo.getText());
            }
            try{
                Thread.sleep(1000);// Esperar un segundo
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            if (i == 0) {
                // Aquí se llama la función juego terminado debido a que el tiempo se ha agotado.
                //noVolverEjecutar es verdadero cuando se termina el juego por otros medios y se quiere
                //terminar con el hilo del temporizador
                if(noVolverEjecutar == false) Platform.runLater(() -> juegoTerminado());
                break;
            }
        }
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
        lblPregunta.getStyleClass().add("labels");
        borderPane.getStyleClass().add("fondo");
        splitted.getStyleClass().add("fondo");
        questionView.getStyleClass().add("chart");
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
            i = 60;
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
            //has perdido
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("¡Has perdido!");
            alert.setHeaderText("Recuerda que puedes volver a participar :)");
            alert.setContentText("Da click en el botón para cerrar la ventana");
            noVolverEjecutar = true;
            i=0;
            
            
            ButtonType closeButton = new ButtonType("Aceptar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(closeButton);

            alert.setOnCloseRequest(event -> {
                try {
                    goBack();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            alert.showAndWait();
        }
    }

    public void comodin5050(){
        if(comodines.contains(TipoComodin.COMODIN5050) == false){
            //boton a -> 0
            //boton b -> 1
            //boton c -> 2
            //boton d -> 3
            
            //se obtiene el indice de la respuesta correcta
            int index = listAnswers.indexOf(currentPreg.getRespuestaCorrecta());

            int borrar1;
            int borrar2;
            //se generan indices de las respuestas a borrar hasta que sean distintas
            //entre si y distintas al indice de la respuesta correcta
            do{
                borrar1 = (int)(Math.random()*4);
                borrar2 = (int)(Math.random()*4);
            }while(borrar1 == index || borrar2 == index || borrar1 == borrar2);
            
            //se verifica que boton es el que se debe desactivar
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

    public void timer(){
        Thread thread = new Thread(this);
        thread.start();
    }
}