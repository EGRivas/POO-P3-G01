module com.juegoimpoosible {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.juegoimpoosible to javafx.fxml;
    exports com.juegoimpoosible;
}
