package com.edusk;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        // Stage stage = new stage();

        Group root = new Group();
        Scene scene = new Scene(root, Color.BLACK);

        Image icon = new Image(getClass().getResourceAsStream("/cocar.png"));
        stage.getIcons().add(icon);
        stage.setWidth(420);
        stage.setHeight(420);
        stage.setResizable(false);
        // stage.setX(50);
        // stage.setY(50);
        stage.setFullScreen(true);
        stage.setFullScreenExitHint("YOU ARE STUCK HERE FOREVER");
        stage.setFullScreenExitKeyCombination(KeyCombination.valueOf("q"));

        stage.setTitle("Stage Demo Program");
        stage.setScene(scene);
        stage.show();
    }
}
