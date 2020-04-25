package rmi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
//TODO: wylaczenie ma sie rownac wykluczeniem z list w spisie
public class ClientApplication extends Application {

    public ClientApplication(){
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("ClientGUI.fxml"));
        Scene scene = new Scene(root,700,645);
        scene.getStylesheets().add(getClass().getResource("/resources/application.css").toExternalForm());
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
