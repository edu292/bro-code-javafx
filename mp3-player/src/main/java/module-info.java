module com.edusk {
    requires javafx.controls;
    requires javafx.media;
    requires javafx.fxml;

    opens com.edusk to javafx.fxml;

    exports com.edusk;
}
