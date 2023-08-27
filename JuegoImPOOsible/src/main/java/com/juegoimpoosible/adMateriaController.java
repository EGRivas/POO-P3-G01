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
    private void chart(MouseEvent event) {
        Materia selectedMateria = subchart.getSelectionModel().getSelectedItem();
        if (selectedMateria != null) {
            if (editSub.isSelected()) {
                subName.setText(selectedMateria.getNombre());
                subCode.setText(selectedMateria.getCodigo());
                // Set other fields as needed
                LevelQuantity.setValue(String.valueOf(selectedMateria.getCantidadNiveles()));
                prevSubject = selectedMateria;  // Actualizar la referencia prevSubject
                prevYear = year.getText();  // Actualizar la referencia prevYear
                prevTerm = numTerm.getText();  // Actualizar la referencia prevTerm
            } else {
                clearFields(); // Clear fields if not editing
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
    private void saveChanges(MouseEvent event) throws IOException {
        ArrayList<Termino> terms = Archivar.readTerms();
        ArrayList<Materia> subjects = Archivar.readSubjects();

        String nombre = subName.getText();
        String anio = year.getText();
        String code = subCode.getText();
        String termi = numTerm.getText();
        int levels = Integer.parseInt((String) LevelQuantity.getValue());

        if (addSub.isSelected()) {
            boolean isValid = true;

            if (nombre.isEmpty() || code.isEmpty() || anio.isEmpty() || termi.isEmpty()) {
                showAlert(AlertType.WARNING, "Campos Vacíos", "Por favor, llene todos los campos.");
                return;
            } else {
                for (Termino t : terms) {
                    if (t.getAnio().equals(anio) && t.getPeriodo().equals(termi)) {
                        for (Materia m : t.getMaterias()) {
                            if (m.getCodigo().equals(code) || m.getNombre().equals(nombre)) {
                                isValid = false;
                                showAlert(AlertType.WARNING, "Materia Existente", "Una materia con el mismo código o nombre ya existe en el mismo término.");
                                break;
                            }
                        }
                    }
                }
                if (isValid) {
                    for (Termino t : terms) {
                        if (t.getAnio().equals(anio) && t.getPeriodo().equals(termi)) {
                            Materia m = new Materia(code, nombre, levels);
                            t.getMaterias().add(m);
                            subjects.add(m);
                            subchart.getItems().add(m);
                            Archivar.writeTerms(terms);
                            Archivar.writeMaterias(subjects);
                            showAlert(AlertType.INFORMATION, "Materia Agregada", "La materia se ha agregado exitosamente.");
                            clearFields();
                            return;
                        }
                    }
                    showAlert(AlertType.WARNING, "Término no Encontrado", "No se encontró el término ingresado.");
                }
            }
        } else if (editSub.isSelected()) {
            if (prevSubject != null && prevYear != null && prevTerm != null) {
                boolean isValid = true;

                if (nombre.isEmpty() || code.isEmpty() || anio.isEmpty() || termi.isEmpty()) {
                    isValid = false;
                    showAlert(AlertType.WARNING, "Campos Vacíos", "Por favor, llene todos los campos.");

                } else {

                    if (isValid) {
                        for (Termino t : terms) {
                            if (t.getAnio().equals(anio) && t.getPeriodo().equals(termi)) {
                                prevSubject.editarMateria(nombre, code);
                                prevSubject.setNivel(levels);

                                for (Termino t1 : terms) {
                                    if (t1.getAnio().equals(prevYear) && t1.getPeriodo().equals(prevTerm)) {
                                        for (int i = 0; i < t.getMaterias().size(); i++) {
                                            if (t1.getMaterias().get(i) == prevSubject) {
                                                t1.getMaterias().set(i, prevSubject);
                                                break;
                                            }
                                        }
                                        break;
                                    }
                                }

                                Archivar.writeTerms(terms);
                                Archivar.writeMaterias(subjects);

                                showAlert(AlertType.INFORMATION, "Materia Editada", "La materia se ha editado exitosamente.");
                                clearFields();
                                return;

                            }
                        }
                        showAlert(AlertType.WARNING, "Término no Encontrado", "No se encontró el término ingresado.");
                    }
                }
            } else {
                showAlert(AlertType.WARNING, "Selección Incompleta", "Por favor, seleccione una materia para editar.");
            }
        } else {
            showAlert(AlertType.WARNING, "Selección Incompleta", "Por favor, seleccione si desea agregar o editar.");
        }
    }

    private void showAlert(AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private void clearFields() {
        subName.clear();
        subCode.clear();
        year.clear();
        numTerm.clear();
        LevelQuantity.setValue(null);
    }

    @FXML
    private MenuButton menuseichon;

    @FXML
    private void goBack(MouseEvent event) throws IOException {
        App.setRoot("configuracion");
    }


}
