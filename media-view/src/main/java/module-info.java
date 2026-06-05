module com.edusk {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.edusk to javafx.fxml;

    exports com.edusk;
}
