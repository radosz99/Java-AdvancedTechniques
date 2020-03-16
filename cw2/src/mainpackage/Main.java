package mainpackage;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import generators.*;
import base.*;
import algs.*;


public class Main extends Application{

	public static void main(String[] args) {
		
		launch(args);
		
		
		int range = 5;
		int size = 10;
		IntElementGenerator data = new IntElementGenerator();
		FloatElementGenerator data2 = new FloatElementGenerator();
		List<IntElement> intList = new ArrayList<>();
		List<FloatElement> floatList = new ArrayList<>();
		PigeonHoleSort ps = new PigeonHoleSort();	
		CountingSort cs = new CountingSort();
		QuickSort qs = new QuickSort();
		InsertSort is = new InsertSort();

		intList = data.getIntData(size,range);			//liczba elementow i zakres (0-x)
		floatList = data2.getFloatData(size,range); 	//liczba elementow i zakres (0-x)
		showList(floatList);
		System.out.println("\n");
		showList(qs.solve(floatList));

	}
	
	public static <T> void showList(List<T> list) {
		for(T i : list) 
			System.out.println(i);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setTitle("Tytul");
		primaryStage.setScene(scene);
		primaryStage.show();
		 
	}

	
}
	

