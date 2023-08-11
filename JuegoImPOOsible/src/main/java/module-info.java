module com.juegoimpoosible {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.juegoimpoosible to javafx.fxml;
    exports com.juegoimpoosible;
}
