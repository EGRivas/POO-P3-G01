package com.juegoimpoosible;
import com.juegoimpoosible.funcionalidad.Archivar;
import com.juegoimpoosible.funcionalidad.Juego;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class ReporteController {
    @FXML
    private TextField yearPeriodTextField;
    @FXML
    private TextField subjectCodeTextField;
    @FXML
    private TextField parallelNumberTextField;
    @FXML
    private ListView<String> Fecha;
    @FXML
    private ListView<String> Participante;

    @FXML
    private ListView<String> NivelMaximo;

    @FXML
    private ListView<String> Tiempo;

    @FXML
    private ListView<String> PreguntasContestadas;

    @FXML
    private ListView<String> ComodinesUsados;

    @FXML
    private ListView<String> Premio;

    @FXML
    private ListView<String> Opciones;
    @FXML
    private void goToMenu(MouseEvent event) throws IOException {
        App.setRoot("menu");
    }

    @FXML
    private void buscarJuegos(MouseEvent event) {
        String yearPeriod = yearPeriodTextField.getText();
        String subjectCode = subjectCodeTextField.getText();
        String parallelNumber = parallelNumberTextField.getText();

        // Read all games using the Archivar class
        ArrayList<Juego> allGames = Archivar.readGames();

        // Filter the games based on user input
        ArrayList<Juego> filteredGames = allGames.stream()
                .filter(juego -> {
                    boolean yearPeriodMatch = juego.getFecha().contains(yearPeriod);
                    boolean subjectCodeMatch = juego.getCodigoMateria().equalsIgnoreCase(subjectCode);
                    boolean parallelNumberMatch = juego.getNumParalelo().equalsIgnoreCase(parallelNumber);
                    return yearPeriodMatch && subjectCodeMatch && parallelNumberMatch;
                })
                .collect(Collectors.toCollection(ArrayList::new));

        // Clear previous results
        Fecha.getItems().clear();

        // Display the matching game dates in the ListView
        for (Juego game : filteredGames) {
            Fecha.getItems().add(game.getFecha());
        }
        Participante.getItems().clear();
        NivelMaximo.getItems().clear();
        Tiempo.getItems().clear();
        PreguntasContestadas.getItems().clear();
        ComodinesUsados.getItems().clear();
        Premio.getItems().clear();
        Opciones.getItems().clear();

        // Display the matching game information in respective ListViews
        for (Juego game : filteredGames) {
            // Display participant's name
            Participante.getItems().add(game.getParticipante().getNombre());

            // Display the level reached
            NivelMaximo.getItems().add(String.valueOf(game.getNivelAlcanzado()));

            // Display the time (number of questions answered * 55)
            Tiempo.getItems().add(String.valueOf(game.getCantidadPreguntasContestadas() * 55));

            // Display the number of questions answered
            PreguntasContestadas.getItems().add(String.valueOf(game.getCantidadPreguntasContestadas()));

            // Display the number of used comodines
            ComodinesUsados.getItems().add(String.valueOf(game.getComodinesUtilizados()));

            // Display the prize won
            Premio.getItems().add(String.valueOf(game.getPremio()));

            // Display "Ver más" as an option
            Opciones.getItems().add("Ver más");
        }
        Opciones.setOnMouseClicked(event1 -> {
            int selectedIndex = Opciones.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                // Get the selected game
                Juego selectedGame = filteredGames.get(selectedIndex);

                // Create and configure an alert
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Detalles del Juego");
                alert.setHeaderText(null);

                // Set alert content
                String contentText = "Fecha: " + selectedGame.getFecha() + "\n" +
                        "Participante: " + selectedGame.getParticipante().getNombre() + "\n" +
                        "Compañero: " + selectedGame.getComApoyo().getNombre() + "\n" +
                        "Nivel Maximo: " + selectedGame.getNivelMax() + "\n" +
                        "Tiempo: " + (selectedGame.getCantidadPreguntasContestadas() * 55) + "\n" +
                        "Premio: " + selectedGame.getPremioGanado();
                alert.setContentText(contentText);

                // Show the alert
                alert.showAndWait();
            }
        });
    }


}
