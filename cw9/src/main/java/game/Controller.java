package game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.TilePane;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

public class Controller implements Initializable {
    @FXML TilePane mainPanel = new TilePane();
    @FXML Button btn = new Button();
    private static List<Boolean> tilesValue = new ArrayList<Boolean>(Collections.nCopies(25, false));

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        Image imgEmpty = new Image(new File("src/main/resources/empty.png").toURI().toString());
//        Image imgX = new Image(new File("src/main/resources/x.png").toURI().toString());
//        Image imgO = new Image(new File("src/main/resources/o.png").toURI().toString());
        Image imgEmpty = new Image(getClass().getResourceAsStream("/empty.png"));
        Image imgX = new Image(getClass().getResourceAsStream("/x.png"));
        Image imgO = new Image(getClass().getResourceAsStream("/o.png"));
        btn.setText("HEJKA");
        List<ImageView> tiles = new ArrayList<>();
        for(int i =0; i< 25; i++){
            tiles.add(new ImageView());
            tiles.get(i).setFitWidth(90);
            tiles.get(i).setFitHeight(90);
            tiles.get(i).setPreserveRatio(true);
            tiles.get(i).setImage(imgEmpty);
            mainPanel.getChildren().add((tiles.get(i)));
            int finalI = i;
            tiles.get(i).setOnMouseClicked( e-> {
                if(e.getButton()== MouseButton.PRIMARY) {
                    if (!tilesValue.get(finalI)) {
                        tiles.get(finalI).setImage(imgX);
                        tilesValue.set(finalI, true);
                        System.err.println("Ustawione");
                    }
                }
                else if(e.getButton()== MouseButton.SECONDARY) {
                    if (!tilesValue.get(finalI)) {
                        tiles.get(finalI).setImage(imgO);
                        tilesValue.set(finalI, true);
                        System.err.println("Ustawione");
                    }
                }
            });
        }
        //mainPanel.setTileAlignment(Pos.TOP_LEFT);
        mainPanel.setHgap(9);
        mainPanel.setVgap(9);


    }




}
