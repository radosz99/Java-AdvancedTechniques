package game;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller implements Initializable {
    @FXML TilePane mainPanel = new TilePane();
    @FXML Button btn = new Button();
    @FXML public TextArea log;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    final private short tilesToWin = 4;
    private static String logsString="";
    private int turn = 0;  // X
    private static List<List<TilesValue>> tilesValue = new ArrayList<List<TilesValue>>(5);
    private Image imgEmpty = new Image(getClass().getResourceAsStream("/empty.png"));
    private Image imgX = new Image(getClass().getResourceAsStream("/x.png"));
    private Image imgO = new Image(getClass().getResourceAsStream("/o.png"));
    private Image winImg = new Image(getClass().getResourceAsStream("/win.png"));
    private List<ImageView> tiles = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for(int i = 0; i < 5; i++){
            List<TilesValue> singleList = new ArrayList<>();
            for (int j = 0; j < 5; j++){
                singleList.add(TilesValue.EMPTY);
            }
            tilesValue.add(singleList);
        }
        btn.setText("HEJKA");
        for(int i = 0; i < 25; i++){
            tiles.add(new ImageView());
            tiles.get(i).setFitWidth(90);
            tiles.get(i).setFitHeight(90);
            tiles.get(i).setPreserveRatio(true);
            tiles.get(i).setImage(imgEmpty);
            mainPanel.getChildren().add((tiles.get(i)));
            int finalI = i;
            tiles.get(i).setOnMouseClicked( e-> {
                if(e.getButton()== MouseButton.PRIMARY) {
                    if (tilesValue.get(finalI/5).get(finalI%5)==TilesValue.EMPTY && turn==0) {
                        tiles.get(finalI).setImage(imgX);
                        tilesValue.get(finalI/5).set(finalI%5, TilesValue.X);
                        turn = 1;
                        showMessage("Player A put X on [" + finalI/5 + "][" + finalI%5 + "]");
                        checkIfWin(finalI/5, finalI%5, TilesValue.X);
                    }
                }
                else if(e.getButton()== MouseButton.SECONDARY) {
                    if (tilesValue.get(finalI/5).get(finalI%5)==TilesValue.EMPTY && turn==1) {
                        tiles.get(finalI).setImage(imgO);
                        tilesValue.get(finalI/5).set(finalI%5, TilesValue.O);
                        turn = 0;
                        showMessage("Player B put O on [" + finalI/5 + "][" + finalI%5 + "]");
                        checkIfWin(finalI/5, finalI%5, TilesValue.O);
                    }
                }
            });
        }
        //mainPanel.setTileAlignment(Pos.TOP_LEFT);
        mainPanel.setHgap(9);
        mainPanel.setVgap(9);
    }

    private void showMessage(String message){
        logsString += dtf.format(LocalDateTime.now()) + "\t" + message + "\n";
        log.setText(logsString);
    }

    private boolean checkIfTileFilled(int x, int y, TilesValue value){
        if(x > 4 || y > 4 || x < 0 || y < 0){
            return false;
        }
        if (tilesValue.get(x).get(y) == value) {
            return true;
        }

        return false;
    }

    private boolean checkIfWin(int x, int y, TilesValue value){
        // right or left
        if (checkIfTileFilled(x, y + 1, value) || checkIfTileFilled(x, y - 1, value)) {
            if(checkHowManyTiles(x, y, value, Course.HORIZONTAL) >= tilesToWin - 1) {
                winAlert(value);
            }
        }
        // up or down
        if (checkIfTileFilled(x - 1, y, value) || checkIfTileFilled(x + 1, y, value)) {
            if(checkHowManyTiles(x, y, value, Course.VERTICAL) >= tilesToWin - 1) {
                winAlert(value);
            }
        }
        // up right or down left
        if (checkIfTileFilled(x - 1, y + 1, value) || checkIfTileFilled(x + 1, y - 1, value)) {
            if(checkHowManyTiles(x, y, value, Course.DIAGONAL_DOWN) >= tilesToWin - 1) {
                winAlert(value);
            }
        }
        // up left or down right
        if (checkIfTileFilled(x - 1, y - 1, value) || checkIfTileFilled(x + 1, y + 1, value)) {
            if(checkHowManyTiles(x, y, value, Course.DIAGONAL_UP) >= tilesToWin - 1) {
                winAlert(value);
            }
        }
        return true;
    }

    private int checkHowManyTiles(int x, int y, TilesValue value, Course course){
        switch(course){
            case HORIZONTAL:
                return checkHowManyTilesInDirection(x, y, value, Direction.LEFT) + checkHowManyTilesInDirection(x, y, value, Direction.RIGHT);
            case VERTICAL:
                return checkHowManyTilesInDirection(x, y, value, Direction.UP) + checkHowManyTilesInDirection(x, y, value, Direction.DOWN);
            case DIAGONAL_DOWN:
                return checkHowManyTilesInDirection(x, y, value, Direction.UP_RIGHT) + checkHowManyTilesInDirection(x, y, value, Direction.DOWN_LEFT);
            case DIAGONAL_UP:
                return checkHowManyTilesInDirection(x, y, value, Direction.DOWN_RIGHT) + checkHowManyTilesInDirection(x, y, value, Direction.UP_LEFT);
        }
        return 0;
    }

    private int checkHowManyTilesInDirection(int x, int y, TilesValue value, Direction direction){
        int counter = 0;
        switch(direction){
            case DOWN:
                while(checkIfTileFilled(x + 1 + counter, y, value)){
                    counter++;
                }
                break;
            case UP:
                while(checkIfTileFilled(x - 1 - counter, y, value)){
                    counter++;
                }
                break;
            case DOWN_LEFT:
                while(checkIfTileFilled(x + 1 + counter, y - 1 - counter, value)){
                    counter++;
                }
                break;
            case DOWN_RIGHT:
                while(checkIfTileFilled(x + 1 + counter, y + 1 + counter, value)){
                    counter++;
                }
                break;
            case UP_LEFT:
                while(checkIfTileFilled(x - 1 - counter, y - 1 - counter, value)){
                    counter++;
                }
                break;
            case UP_RIGHT:
                while(checkIfTileFilled(x - 1 - counter, y + 1 + counter, value)){
                    counter++;
                }
                break;
            case LEFT:
                while(checkIfTileFilled(x, y - 1 - counter, value)){
                    counter++;
                }
                break;
            case RIGHT:
                while(checkIfTileFilled(x, y + 1 + counter, value)){
                    counter++;
                }
                break;
        }
        return counter;
    }

    private void winAlert(TilesValue tilesValue) {
        ImageView img = new ImageView();
        ButtonType buttonTypeOne = new ButtonType("Ok");
        img.setImage(winImg);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("End of the game");
        alert.setGraphic(img);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/tik.png")));
        if(tilesValue == TilesValue.O){
            alert.setHeaderText("Player B has won");
        } else if(tilesValue == TilesValue.X){
            alert.setHeaderText("Player A has won");
        }
        alert.getButtonTypes().setAll(buttonTypeOne);
        alert.showAndWait();
    }
}
