package application;
	
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;


public class Main extends Application{
	private static Stage stage;
	private static FXMLLoader loader;
	
	public static Stage getPrimaryStage() {
		return stage;
	}
	
	public static FXMLLoader getLoader() {
		return loader;
	}

	@Override
	public void start(Stage primaryStage) {	
		try {
			ResourceBundle bundle = ResourceBundle.getBundle("resources.bundles");
			loader = new FXMLLoader(this.getClass().getResource("/application/Main.fxml"));
			stage = primaryStage;
			loader.setResources(bundle);
			Parent root = loader.load();
			Scene scene = new Scene(root,890,700);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			primaryStage.setResizable(false);
			primaryStage.getIcons().add(new Image("file:icon.png"));
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);
	}
	
	
}
