package rmi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ServerApplication extends Application {

    public ServerApplication(){
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/rmi/server/ServerGUI.fxml"));
        Scene scene = new Scene(root,600,450);
        scene.getStylesheets().add(getClass().getResource("/rmi/server/server.css").toExternalForm());
        primaryStage.setTitle("Sorting server");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
