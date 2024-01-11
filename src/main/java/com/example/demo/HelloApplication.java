package com.example.demo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Timer (1).fxml"));
        Parent root= fxmlLoader.load();
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        HelloController controller = fxmlLoader.getController();
        if(controller!=null) {
            controller.setStage(stage);
            controller.setRoot(root);
        }
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}