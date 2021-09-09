package main.java.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception{

        Parent root =  FXMLLoader.load(Optional.ofNullable(getClass()
                .getClassLoader()
                .getResource("IzdatnicaView.fxml")).get());

        Scene scene = new Scene(root);
        scene.getStylesheets().add(String.valueOf(Main.class.getClassLoader().getResource("application.css")));
        stage.setTitle("Skladiste");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
