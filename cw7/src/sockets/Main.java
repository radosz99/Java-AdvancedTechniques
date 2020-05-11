package sockets;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("User");
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args){
        int port = PortGenerator.getPort();
        Listener listener = new Listener(String.valueOf(port));
        listener.start();
        launch(args);
    }
}
