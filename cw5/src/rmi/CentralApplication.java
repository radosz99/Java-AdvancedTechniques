package rmi;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CentralApplication extends Application {

    public CentralApplication(){
}
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/rmi/central/CentralGUI.fxml"));
        Scene scene = new Scene(root,600,550);
        scene.getStylesheets().add(getClass().getResource("/rmi/central/central.css").toExternalForm());
        primaryStage.setTitle("Central");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
