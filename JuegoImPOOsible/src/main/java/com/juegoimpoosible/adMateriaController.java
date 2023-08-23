package com.juegoimpoosible;

import com.juegoimpoosible.funcionalidad.Archivar;
import com.juegoimpoosible.modelo.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class adMateriaController  implements Initializable{

    @FXML
    private ListView<Materia> subchart;
    private Materia prevSubject;
    private String prevTerm;
    private String prevYear;
    @Override
    public void initialize(URL url, ResourceBundle rb){
        LevelQuantity.getItems().addAll("1", "2", "3","4","5","6","7","8","9");
        ArrayList<Termino> terms = Archivar.readTerms();
        ArrayList<Materia> subjects = Archivar.readSubjects();
        subchart.getItems().addAll(subjects);

        subchart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                prevSubject = (Materia) newValue;
                subName.setText(((Materia)newValue).getNombre());
                subCode.setText(((Materia)newValue).getCodigo());
                System.out.println(((Materia) newValue));
                for(Termino t: terms){
                    for(Materia m: t.getMaterias()){
                        if((((Materia)newValue).getNombre()).equals(m.getNombre()) && (((Materia)newValue).getCodigo()).equals(m.getCodigo())){
                            year.setText(prevYear = t.getAnio());
                            numTerm.setText(prevTerm = t.getPeriodo());
                            LevelQuantity.setValue(("" + m.getCantidadNiveles()));
                        }
                    }
                }

            }
        });


    }

    @FXML
    private void mainMenu(){

        MenuItem item2 = new MenuItem("Administrar Paralelos");
        MenuItem item3 = new MenuItem("Administrar Estudiantes");
        menuseichon.getItems().addAll(item2, item3);
        item2.setOnAction(actionEvent -> menuAdParalelos());
        item3.setOnAction(actionEvent -> menuAdEstudiantes());
        if(menuseichon.getItems().size() >= 4){
            menuseichon.getItems().remove(2);
            menuseichon.getItems().remove(2);
        }
    }

    private void menuAdParalelos(){
        try{
            App.setRoot("adParalelo");
        }catch(IOException e){
            System.out.println("[-]No se pudo encontrar");
        }

    }
    private void menuAdEstudiantes(){
        try{
            App.setRoot("adEstudiantes");
        }catch(IOException e){
            System.out.println("[-]No se pudo encontrar");
        }

    }

    @FXML
    private void chart(MouseEvent event){
        ArrayList<Termino> terms = Archivar.readTerms();
        ArrayList<Materia> subjects = Archivar.readSubjects();

        subchart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                prevSubject = (Materia) newValue;
                subName.setText((((Materia)newValue).getNombre()));
                System.out.println(((Materia)newValue).getNombre());
                subCode.setText(((Materia)newValue).getCodigo());
                for(Termino t: terms){
                    for(Materia m: t.getMaterias()){

                        if((((Materia)newValue).getNombre()).equals(m.getNombre()) && (((Materia)newValue).getCodigo()).equals(m.getCodigo())){

                            year.setText((prevYear = t.getAnio()));
                            numTerm.setText((prevTerm = t.getPeriodo()));
                            LevelQuantity.setValue(("" + m.getCantidadNiveles()));
                        }
                    }
                }

            }
        });
    }
    @FXML
    private TextField year;
    @FXML
    private TextField numTerm;
    @FXML
    private TextField subName;
    @FXML
    private TextField subCode;
    @FXML
    private RadioButton editSub;
    @FXML
    private RadioButton addSub;
    @FXML
    private ChoiceBox LevelQuantity;
    @FXML
    private void saveChanges(MouseEvent event) throws IOException{
        ArrayList<Termino> terms = Archivar.readTerms();
        ArrayList<Materia> subjects = Archivar.readSubjects();

        String nombre = subName.getText();
        String anio = year.getText();
        String code = subCode.getText();
        String termi = numTerm.getText();
        int levels = Integer.parseInt(((String) LevelQuantity.getValue()));




        if(editSub.isSelected()){
            System.out.println("DIOSSSSSSSSSS");
            for(Termino t: terms){
                if((t.getAnio()).equals(prevYear) && (t.getPeriodo()).equals(prevTerm)){
                    for(Materia m: subjects){
                        if((m.getNombre()).equals(prevSubject.getNombre()) && (m.getCodigo()).equals(prevSubject.getCodigo())){
                            (t.getMaterias()).remove(m);
                            subjects.remove(m);
                            subchart.getItems().remove(prevSubject);

                        }
                    }
                }
            }

            for(Termino t: terms){
                if((t.getAnio()).equals(anio) && (t.getPeriodo()).equals(termi)){
                    //Materia paso = new Materia(code, nombre, levels);
                    prevSubject.editarMateria(nombre, code);
                    prevSubject.editarMateria(nombre, levels);
                    (t.getMaterias()).add(prevSubject);
                    subjects.add(prevSubject);
                }
            }

            Archivar.writeTerms(terms);
            Archivar.writeMaterias(subjects);
            subchart.getItems().add(prevSubject);


        }else if(addSub.isSelected()){

            for(Termino t: terms){
                if((t.getAnio()).equals(anio) && (t.getPeriodo()).equals(termi)){
                    Materia m = new Materia(code, nombre, levels);
                    (t.getMaterias()).add(m);
                    subjects.add(m);
                    subchart.getItems().add(m);
                }
            }
            Archivar.writeTerms(terms);
            Archivar.writeMaterias(subjects);


        }else{
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("This is a warning message");
            alert.setContentText("Please select whether to add or edit");
        }

    }
    @FXML
    private MenuButton menuseichon;

    @FXML
    private void goBack(MouseEvent event) throws IOException {
        App.setRoot("configuracion");
    }


}
