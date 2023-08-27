package com.juegoimpoosible;

import com.juegoimpoosible.funcionalidad.Archivar;
import com.juegoimpoosible.modelo.Estudiante;
import com.juegoimpoosible.modelo.Materia;
import com.juegoimpoosible.modelo.Paralelo;
import com.juegoimpoosible.modelo.Termino;
import java.io.BufferedReader;
import java.io.FileReader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class adParaleloController implements Initializable {
    @FXML
    private TextField numParalelo;
    @FXML
    private ListView<Materia> subchart;
    @FXML
    private ComboBox availableParalelo;
    @FXML
    private TextField newNumber;
    @FXML
    private MenuButton menuseichon;
    private static Materia prevSubject;
    private static String prevTerm;
    private static String prevYear;
    @FXML
    private TextField nombreArchivo;

    @Override
    public void initialize(URL url, ResourceBundle rb){
        mainMenu();
        ArrayList<Materia> subjects = Archivar.readSubjects();
        if(subchart.getItems() == null){
            subchart.getItems().addAll(subjects);
        } else{
            subchart.getItems().clear();
            subchart.getItems().addAll(subjects);
        }



    }

    @FXML
    private void chart(MouseEvent event) {
        ArrayList<Termino> terms = Archivar.readTerms();
        ArrayList<Materia> subjects = Archivar.readSubjects();

        Materia selectedSubject = subchart.getSelectionModel().getSelectedItem();
        if (selectedSubject != null) {
            prevSubject = selectedSubject;

        }

        for (Termino t : terms) {
            for (Materia m : t.getMaterias()) {
                if (m.equals(prevSubject)) {
                    availableParalelo.getItems().clear();
                    availableParalelo.getItems().addAll(m.getParalelos());


                    break;
                }
            }
        }
    }





    @FXML
    private void saveParalelo(MouseEvent event){
        ArrayList<Termino> terms = Archivar.readTerms();
        ArrayList<Materia> subjects = Archivar.readSubjects();
        ArrayList<Estudiante> listaEst = new ArrayList<>();
        Paralelo paralelo = new Paralelo(numParalelo.getText());
        String ruta = "archivos/"+nombreArchivo.getText()+".csv";
        try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
            br.readLine();
            String line;
            while((line = br.readLine()) != null){
                String[] arr = line.split(";");
                Estudiante e1 = new Estudiante(arr[1],arr[0],arr[2]);
                listaEst.add(e1);
            }
        } catch(IOException e){
            //e.printStackTrace();
            System.err.println("[-]Something Unexpected happened, but it's ok");
            ruta = "JuegoImPOOsible/archivos/"+nombreArchivo.getText()+".csv";
            try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
                br.readLine();
                String line;
                while((line = br.readLine()) != null){
                    String[] arr = line.split(";");
                    Estudiante e1 = new Estudiante(arr[1],arr[0],arr[2]);
                    listaEst.add(e1);
                }
            } catch(IOException ex){
                ex.printStackTrace();}
        }
        paralelo.addEstudiantes(listaEst);
        subchart.getItems().remove(prevSubject);

        for (Termino t : terms) {
            for (Materia m : t.getMaterias()) {
                if (m.equals(prevSubject)) {
                    m.aggParalelo(paralelo);
                    for(Materia o: subjects){
                        if(m.equals(o)){
                            o.aggParalelo(paralelo);
                            subchart.getItems().add(o);
                        }
                    }
                    break;
                }
            }
        }
        Archivar.writeTerms(terms);
        Archivar.writeMaterias(subjects);

    }

    @FXML
    private void editParalelo(MouseEvent event){
        ArrayList<Termino> terms = Archivar.readTerms();
        ArrayList<Materia> subjects = Archivar.readSubjects();
        Paralelo p1 = new Paralelo(newNumber.getText());
        Paralelo pastParalelo = (Paralelo) availableParalelo.getSelectionModel().getSelectedItem();
        Materia apuntaMateria = null;
        Materia apuntaMateria2 = null;
        ArrayList<Estudiante> copyEstudiante = new ArrayList<Estudiante>();
        subchart.getItems().remove(prevSubject);

        for (Termino t : terms) {
            for (Materia m : t.getMaterias()) {
                if (m.equals(prevSubject)) {
                    apuntaMateria = m;
                    for(Materia o: subjects){
                        if(m.equals(o)){
                            apuntaMateria2 = o;
                            for(Paralelo p: apuntaMateria2.getParalelos()){
                                if(p.getEstudiantes() != null){
                                    copyEstudiante = p.getEstudiantes();
                                }
                            }
                        }
                    }
                    break; // Exit the loop once a match is found
                }
            }
        }


        apuntaMateria.getParalelos().remove(pastParalelo);
        apuntaMateria2.getParalelos().remove(pastParalelo);
        p1.addEstudiantes(copyEstudiante);
        apuntaMateria.getParalelos().add(p1);
        apuntaMateria2.getParalelos().add(p1);
        subchart.getItems().remove(apuntaMateria2);
        subchart.getItems().add(apuntaMateria2);
        Archivar.writeMaterias(subjects);
        Archivar.writeTerms(terms);




    }

    @FXML
    private void eliminar(){

        ArrayList<Termino> terms = Archivar.readTerms();
        ArrayList<Materia> subjects = Archivar.readSubjects();
        Paralelo p1 = new Paralelo(newNumber.getText());
        Paralelo pastParalelo = (Paralelo) availableParalelo.getSelectionModel().getSelectedItem();
        Materia apuntaMateria = null;
        Materia apuntaMateria2 = null;
        ArrayList<Estudiante> copyEstudiante = new ArrayList<Estudiante>();
        subchart.getItems().remove(prevSubject);

        for (Termino t : terms) {
            for (Materia m : t.getMaterias()) {
                if (m.equals(prevSubject)) {
                    apuntaMateria = m;
                    for(Materia o: subjects){
                        if(m.equals(o)){
                            apuntaMateria2 = o;
                            for(Paralelo p: apuntaMateria2.getParalelos()){
                                if(p.getEstudiantes() != null){
                                    copyEstudiante = p.getEstudiantes();
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }


        apuntaMateria.getParalelos().remove(pastParalelo);
        apuntaMateria2.getParalelos().remove(pastParalelo);
        subchart.getItems().remove(apuntaMateria2);
        subchart.getItems().add(apuntaMateria2);
        Archivar.writeMaterias(subjects);
        Archivar.writeTerms(terms);

    }

    @FXML
    private void goBack(MouseEvent event) throws IOException {
        App.setRoot("configuracion");
    }

    @FXML
    private void mainMenu(){
        menuseichon.getItems().clear();
        MenuItem item2 = new MenuItem("Administrar Materias");
        //MenuItem item3 = new MenuItem("Administrar Estudiantes");
        menuseichon.getItems().addAll(item2);
        item2.setOnAction(actionEvent -> menuAdMaterias());
        //item3.setOnAction(actionEvent -> menuAdEstudiantes());
        if(menuseichon.getItems().size() >= 4){
            menuseichon.getItems().remove(2);
            menuseichon.getItems().remove(2);
        }
    }

    private void menuAdMaterias(){
        try{
            App.setRoot("adMateria");
        }catch(IOException e){
            System.out.println("[-]No se pudo encontrar");
        }
    }
    /*private void menuAdEstudiantes(){
        try{
            App.setRoot("adEstudiantes");
        }catch(IOException e){
            System.out.println("[-]No se pudo encontrar");
        }
    }*/
}
