module com.edusk {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens com.edusk to javafx.fxml;

    exports com.edusk;
}
