package com.juegoimpoosible;

import com.juegoimpoosible.modelo.*;
import com.juegoimpoosible.funcionalidad.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ConfJuegoController implements Initializable{
    @FXML
    private Label lblTermino;
    @FXML
    private ComboBox<Materia> cmbMateria;
    @FXML
    private ComboBox<Paralelo> cmbParalelo;
    @FXML
    private TextField numPregField;
    @FXML
    private TextField participanteField;
    @FXML
    private TextField apoyoField;
    @FXML
    private CheckBox participanteCheck;
    @FXML
    private CheckBox apoyoCheck;
    
    private boolean cond1; 
    private boolean cond2;
    
    static Juego juegoIn;
    static ArrayList<Materia> listaMaterias = new ArrayList<>(); 
    static ArrayList<Preguntas> preguntas;
    static Materia materiaSelect;
    static Paralelo paraleloSelect;
    static Estudiante participante;
    static Estudiante apoyo;
    
    private boolean valido;
    
    @FXML
    private Button regreso;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cond1 = false;
        cond2 = false;
        //reinicio de los valores
        cmbMateria.getItems().clear();
        listaMaterias.clear();
        //Creacion de estudiantes para la prueba
        ArrayList<Estudiante> lEstudiantesP03 = new ArrayList<>();
        String ruta = "archivos/POO_P3_2023_1T.csv";
        try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
            br.readLine();
            String line;
            while((line = br.readLine()) != null){
                String[] arr = line.split(";");
                Estudiante e1 = new Estudiante(arr[1],arr[0],arr[2]);
                lEstudiantesP03.add(e1);
            }
        } catch(IOException e){
            e.printStackTrace();
            ruta = "JuegoImPOOsible/archivos/POO_P3_2023_1T.csv";
            try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
                br.readLine();
                String line;
                while((line = br.readLine()) != null){
                    String[] arr = line.split(";");
                    Estudiante e1 = new Estudiante(arr[1],arr[0],arr[2]);
                    lEstudiantesP03.add(e1);
                }
            } catch(IOException ex){
                ex.printStackTrace();}
        }
        
        //Paralelo 3 con los estudiantes ya creados, este sera el paralelo de prueba, solo se ingresa el numero
        Paralelo paralelo3 = new Paralelo("3",lEstudiantesP03);
        
        //las preguntas se acceden por medio del enunciado si es que se desea eliminarlas
        //preguntas nivel 1
        ArrayList<String> resp1 = new ArrayList<>();
        resp1.add("append()");
        resp1.add("clear()");
        resp1.add("contains()");
        
        ArrayList<String> resp2 = new ArrayList<>();
        resp2.add("Unidad basica que compone la secuencia logica de Python");
        resp2.add("Lista de arreglos que crean objetos");
        resp2.add("Unidad basica para especificar que hace el programa sin decirle el como");
        
        ArrayList<String> resp3 = new ArrayList<>();
        resp3.add("Un metodo que trabaja con inputs y outputs");
        resp3.add("Funcionalidad del programa a crear");
        resp3.add("Variable que solo aporta informacion");
        
        Preguntas p1 = new Preguntas("El metodo de un ArrayList para agregar elementos es",1,resp1,"add()");
        Preguntas p2 = new Preguntas("¿Que es una clase?",1,resp2,"Unidad basica que define propiedades de un objeto");
        Preguntas p3 = new Preguntas("¿Que es un objeto?",1,resp3,"Representacion abstracta de un concepto");
        
        //preguntas nivel 2
        ArrayList<String> resp4 = new ArrayList<>();
        resp4.add("En constructores");
        resp4.add("En variables de instancia");
        resp4.add("En herencia");
        
        ArrayList<String> resp5 = new ArrayList<>();
        resp5.add("Beat-Boxing");
        resp5.add("JazzBoxing");
        resp5.add("Unboxing");
        
        ArrayList<String> resp6 = new ArrayList<>();
        resp6.add("Agregacion");
        resp6.add("Agrupacion");
        resp6.add("Asociacion Reflexiva");
        
        Preguntas p4 = new Preguntas("¿En donde se puede dar una sobrecarga?",2,resp4,"En metodos y constructores");
        Preguntas p5 = new Preguntas("El paso de un dato primitivo a una clase Wrapper se denomina",2,resp5,"Boxing");
        Preguntas p6 = new Preguntas("Objetos parte asociados a solo un objeto todo, se crean y se destruyen con el",2,resp6,"Composicion");
        
        //preguntas nivel 3
        ArrayList<String> resp7 = new ArrayList<>();
        resp7.add("El tipo de retorno debe ser el mismo de la super clase");
        resp7.add("El nivel de acceso no puede ser más restrictivo que el método que se sobrescribe");
        resp7.add("No se puede sobrescribir un método marcado como final");
        
        ArrayList<String> resp8 = new ArrayList<>();
        resp8.add("Herencia");
        resp8.add("Abstraccion");
        resp8.add("Polimorfismo");
        
        ArrayList<String> resp9 = new ArrayList<>();
        resp9.add("Puede ser instanciada");
        resp9.add("No puede ser extendida");
        resp9.add("Debe incluir minimo un metodo abstracto");
        
        Preguntas p7 = new Preguntas("Las reglas de la sobreescritura son",3,resp7,"Todas las anteriores");
        Preguntas p8 = new Preguntas("¿Cual de estos no es un pilar de la Orientacion a Objetos?",3,resp8,"Embotellamiento");
        Preguntas p9 = new Preguntas("La clase abstracta",3,resp9,"Puede tener constructor");

        
        //Creacion Materia POO con el paralelo 3 y las preguntas ya hechas
        Materia POO = new Materia("CCPG1052","POO",3);
        POO.aggParalelo(paralelo3);
        POO.agregarPregunta(p1);
        POO.agregarPregunta(p2);
        POO.agregarPregunta(p3);
        POO.agregarPregunta(p4);
        POO.agregarPregunta(p5);
        POO.agregarPregunta(p6);
        POO.agregarPregunta(p7);
        POO.agregarPregunta(p8);
        POO.agregarPregunta(p9);
        
        Termino term1 = new Termino("2023","1");
        term1.actuMaterias(POO);
        //setteando por defecto el termino actual 2023-1
        Juego.setTermino(term1);
        
        //Juego creado
        juegoIn = new Juego(POO);
        listaMaterias.add(POO);
        
        //configuracion contenedores
        cmbMateria.getItems().addAll(listaMaterias);
        numPregField.setDisable(true);
        participanteField.setDisable(true);
        participanteCheck.setDisable(true);
        apoyoField.setDisable(true);
        apoyoCheck.setDisable(true);
    }
    
    //carga los paralelos de la materia en el cmbParalelo
    @FXML
    public void cargaParalelos(){
        cmbParalelo.getItems().clear();
        materiaSelect = cmbMateria.getValue();
        cmbParalelo.getItems().addAll(materiaSelect.getParalelos());
    }
    
    @FXML
    public void paraleloSelect(){
        paraleloSelect = cmbParalelo.getValue();
        numPregField.setDisable(false);
        participanteField.setDisable(false);
        participanteCheck.setDisable(false);
        apoyoField.setDisable(false);
        apoyoCheck.setDisable(false);
        
        //esto es solo para probar
        System.out.println(paraleloSelect.getEstudiantes().size());
    }
    
    //metodo ya probado y funcional
    @FXML
    public void validarPreguntas(){
        preguntas = materiaSelect.getPreguntas();
        valido = false;
        //while(valido == false){
            int c = 0; //contador externo
            
            /*
            System.out.println("Ingrese el número de preguntas por nivel:");
            int num = sc.nextInt(); //numero de preguntas por nivel
            sc.nextLine();
            */
            
            int num = Integer.parseInt((numPregField.getText()));
            //se hace una lista de enteros con el numero de niveles que hay en cada pregunta
            ArrayList<Integer> niveles = new ArrayList<>(); 
            for (Preguntas preg: preguntas){
                niveles.add(preg.getNivel()); //agregando el numero de nivel de la pregunta a la lista "niveles"
            }
            int maxNivel = Collections.max(niveles); //el nivel maximo encontrado en las preguntas
            for (int x = 1;x<=maxNivel;x++){ //primer lazo for que itera del 1 al nivelMaximo con un contador interno
                int c1 = 0;
                for(int n : niveles){ //se recorre la lista de niveles
                    if(n==x){ //se compara que el nivel sea igual a la iteracion del lazo for (el nivel analizado)
                       c1++; //se ira aumentando este contador interno hasta que se lea toda la lista
                    }//En la primera iteracion (x=1), si hay 3 preguntas de nivel 1, este contador da como resultado 3
                }
                if(c1>=num){//se aumenta el contador externo si el interno supera o es igual a num
                    c++;//es decir, siendo num = 3
                    //si hay 4 preguntas en el nivel 1, 4>=3 es true, por lo que queda validado el nivel 1
                }//si hay 2 preguntas en el nivel 2, 2>=3 es false, por lo que el nivel 2 no es validado y c no aumenta
            }
            if(c==maxNivel){//se valida que todos los niveles hayan aumentado, por lo que el maximo debe ser igual a c
                valido = true;
                juegoIn.setNumPreNivel(num);
                juegoIn.setNivelMax(maxNivel); //nivel maximo de preguntas
            } else {
                System.out.println("mensaje de alerta, valor incorrecto");
                valido = false;
            }
        //}
        //mensaje para probar
        System.out.println(valido);
    }
    
    public Estudiante verificarEstudiante(String matricula){
        ArrayList<Estudiante> lEstudiantes = paraleloSelect.getEstudiantes();
        Estudiante verificarEst = new Estudiante(matricula);
        if(lEstudiantes.contains(verificarEst)){
            Estudiante resultado = lEstudiantes.get(lEstudiantes.indexOf(verificarEst));
            return resultado;
        }
        return null;
    }
    
    //seleccion manual por medio de matricula
    @FXML
    public void usoParticipante(){
        if(verificarEstudiante(participanteField.getText()) != null){
            participante = verificarEstudiante(participanteField.getText());
            cond1=true;
            System.out.println(participante.getNombre());
        } else {
            System.out.println("aqui sale un mensaje de que no se encontro");
            cond1=false;
        }
    }
    
    //seleccion aleatoria
    @FXML
    public void participanteAleatorio(){
        if(participanteCheck.isSelected()){
            participanteField.clear();
            participanteField.setDisable(true);
            cond1=true;
            int index = (int)(Math.random() * paraleloSelect.getEstudiantes().size());
            participante = paraleloSelect.getEstudiantes().get(index);
            System.out.println(participante.getNombre());
        } else{
            participanteField.setDisable(false);
            cond1=false;
        }
    }
    
    //seleccion aleatoria
    @FXML
    public void apoyoAleatorio(){
        if(apoyoCheck.isSelected()){
            apoyoField.clear();
            apoyoField.setDisable(true);
            cond2=true;
            int index = (int)(Math.random() * paraleloSelect.getEstudiantes().size());
            apoyo = paraleloSelect.getEstudiantes().get(index);
            System.out.println(apoyo.getNombre());
        } else{
            apoyoField.setDisable(false);
            cond2=false;
        }
    }
    
    //seleccion por matricula
    @FXML
    public void usoApoyo(){
        if(verificarEstudiante(apoyoField.getText()) != null){
            apoyo = verificarEstudiante(apoyoField.getText());
            cond2=true;
            System.out.println(apoyo.getNombre());
        } else {
            System.out.println("aqui sale un mensaje de que no se encontro");
            cond2=false;
        }
    }
    
    
    
    //boton de regreso
    @FXML
    private void goBack() throws IOException {
        App.setRoot("menu");
    }
    //boton de inicio de juego
    @FXML
    private void goToJuegoView() throws IOException {
        if(valido == cond1 == cond2 == true){
            App.setRoot("juegoView");   
        }
    }
    
}
