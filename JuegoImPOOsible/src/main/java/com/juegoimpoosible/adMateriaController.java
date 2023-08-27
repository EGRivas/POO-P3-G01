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
    private static Materia prevSubject;
    private static String prevTerm;
    private static String prevYear;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mainMenu();
        LevelQuantity.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9");
        ArrayList<Termino> terms = Archivar.readTerms();
        ArrayList<Materia> subjects = Archivar.readSubjects();
        subchart.getItems().addAll(subjects);

        subchart.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                prevSubject = newValue;
                subName.setText(newValue.getNombre());
                subCode.setText(newValue.getCodigo());

                for (Termino t : terms) {
                    for (Materia m : t.getMaterias()) {
                        if (m.equals(newValue)) {
                            year.setText(prevYear = t.getAnio());
                            numTerm.setText(prevTerm = t.getPeriodo());
                            LevelQuantity.setValue("" + m.getCantidadNiveles());
                            break; // Exit the loop once a match is found
                        }
                    }
                }
            }
        });
    }


    @FXML
    private void mainMenu(){
        menuseichon.getItems().clear();
        MenuItem item2 = new MenuItem("Administrar Paralelos");
        //MenuItem item3 = new MenuItem("Administrar Estudiantes");
        menuseichon.getItems().add(item2);
        item2.setOnAction(actionEvent -> menuAdParalelos());
        //item3.setOnAction(actionEvent -> menuAdEstudiantes());
        if(menuseichon.getItems().size() >= 4){
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
    private void chart(MouseEvent event) {
        ArrayList<Termino> terms = Archivar.readTerms();
        ArrayList<Materia> subjects = Archivar.readSubjects();

        Materia selectedSubject = subchart.getSelectionModel().getSelectedItem();
        if (selectedSubject != null) {
            prevSubject = selectedSubject;
            subName.setText(selectedSubject.getNombre());
            subCode.setText(selectedSubject.getCodigo());

            for (Termino t : terms) {
                for (Materia m : t.getMaterias()) {
                    if (m.equals(selectedSubject)) {
                        year.setText(prevYear = t.getAnio());
                        numTerm.setText(prevTerm = t.getPeriodo());
                        LevelQuantity.setValue("" + m.getCantidadNiveles());
                        break; // Exit the loop once a match is found
                    }
                }
            }
        }
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
        int levels;
        try {
            levels = Integer.parseInt(((String) LevelQuantity.getValue()));
        }catch (Exception e){
            levels = 0;
        }

        if(!(nombre.isEmpty() || anio.isEmpty() || code.isEmpty() || termi.isEmpty() || levels == 0)){
            if(editSub.isSelected()){
                Materia desesperacion = new Materia("12", "pablo", 5);
                Termino terminoPrueba = new Termino(prevYear, prevTerm);
                Termino terminoApunta = null;
                for(Termino t: terms){
                    if(t.equals(terminoPrueba)){
                        for (Materia q: subjects) {
                            if (q.equals(prevSubject)) {
                                desesperacion = q;
                                terminoApunta = t;



                            }
                        }
                    }
                }
                (terminoApunta.getMaterias()).remove(desesperacion);
                subjects.remove(desesperacion);

                for(Termino w: terms){

                    if(w.equals(new Termino(anio, termi))){
                        terminoApunta = w;

                        //Materia paso = new Materia(code, nombre, levels);
                        desesperacion.editarMateria(nombre, code);
                        desesperacion.editarMateria(nombre, levels);

                        subjects.add(desesperacion);

                    }
                }
                (terminoApunta.getMaterias()).add(desesperacion);
                Archivar.writeMaterias(subjects);
                Archivar.writeTerms(terms);
                subchart.getItems().add(desesperacion);
                subchart.getItems().remove(prevSubject);


            }else if(addSub.isSelected()){

                for(Termino t: terms){
                    if(t.equals(new Termino(anio, termi))){
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
        } else {
            showAlert(AlertType.WARNING, "Campos Vacíos", "Por favor, llene todos los campos.");
        }




    }

    private void showAlert(AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private MenuButton menuseichon;

    @FXML
    private void goBack(MouseEvent event) throws IOException {
        App.setRoot("configuracion");
    }


}