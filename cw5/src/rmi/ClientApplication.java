package rmi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClientApplication extends Application {

    public static Stage getStage() {
        return stage;
    }

    private static Stage stage;

    public ClientApplication(){
    }
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/rmi/client/ClientGUI.fxml"));
        Scene scene = new Scene(root,700,500);
        stage = primaryStage;
        scene.getStylesheets().add(getClass().getResource("/rmi/client/client.css").toExternalForm());
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
