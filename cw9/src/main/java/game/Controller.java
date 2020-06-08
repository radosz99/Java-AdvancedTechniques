package game;

import game.algorithm.Handler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Controller implements Initializable {
    @FXML TilePane mainPanel = new TilePane();
    @FXML Button clearBtn = new Button();
    @FXML public TextArea log;
    @FXML ComboBox<String> strategyCmb = new ComboBox<>();
    @FXML ComboBox<String> langCmb = new ComboBox<>();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    final private short tilesToWin = 4;
    final private short boardSize = 5;
    private static String logsString="";
    private List<List<TilesValue>> tilesValue = new ArrayList<>(boardSize);
    private Image imgEmpty = new Image(getClass().getResourceAsStream("/empty.png"));
    private Image imgX = new Image(getClass().getResourceAsStream("/x.png"));
    private Image imgO = new Image(getClass().getResourceAsStream("/o.png"));
    private Image winImg = new Image(getClass().getResourceAsStream("/win.png"));
    private List<List<ImageView>> tiles = new ArrayList<>(boardSize);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        langCmb.getItems().addAll("Javascript", "C++");
        log.setEditable(false);
        for(int i = 0; i < boardSize; i++){
            List<TilesValue> singleList = new ArrayList<>();
            List<ImageView> singleListImg = new ArrayList<>();
            for (int j = 0; j < boardSize; j++){
                singleList.add(TilesValue.EMPTY);
                singleListImg.add(new ImageView(imgEmpty));
                singleListImg.get(j).setFitWidth(90);
                singleListImg.get(j).setFitHeight(90);
                singleListImg.get(j).setPreserveRatio(true);
                mainPanel.getChildren().add(singleListImg.get(j));
            }
            tiles.add(singleListImg);
            tilesValue.add(singleList);
        }

        strategyCmb.setOnMouseClicked(e->{
            String lang = langCmb.getSelectionModel().getSelectedItem();
            if(lang==null){
                return;
            }
            if(lang.equals("Javascript")){
                strategyCmb.getItems().clear();
                strategyCmb.getItems().addAll("Random", "Simply", "Minimax");
            }
            else {
                strategyCmb.getItems().clear();
                strategyCmb.getItems().addAll("Random", "Simply");
            }
        });

        for(int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++) {
                int finalI = i;
                int finalJ = j;
                tiles.get(i).get(j).setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY && tilesValue.get(finalI).get(finalJ) == TilesValue.EMPTY ) {
                        tiles.get(finalI).get(finalJ).setImage(imgX);
                        tilesValue.get(finalI).set(finalJ, TilesValue.X);
                        showMessage("Player A put X on [" + finalI + "][" + finalJ + "]");
                        if (checkIfWin(finalI, finalJ, TilesValue.X)) {
                            winAlert(TilesValue.X);
                        }
                        String strategy = strategyCmb.getSelectionModel().getSelectedItem();
                        String lang = langCmb.getSelectionModel().getSelectedItem();
                        Pair<Integer, Integer> coords = Handler.makeMove(strategy, lang, tilesValue);
                        int x = coords.getKey();
                        int y = coords.getValue();

                        if(x >= boardSize || y >= boardSize){
                            return;
                        }
                        System.out.println("X = " + x + ", y = " + y);
                        tiles.get(x).get(y).setImage(imgO);
                        tilesValue.get(x).set(y, TilesValue.O);
                        showMessage("Computer put O on [" + x + "][" + y + "]");
                        if (checkIfWin(x, y, TilesValue.O)) {
                            winAlert(TilesValue.O);
                        }
                    }
                });
            }
        }
        mainPanel.setHgap(9);
        mainPanel.setVgap(9);

        clearBtn.setOnMouseClicked( e -> {
            for(int i = 0; i < boardSize; i++){
                for (int j = 0; j < boardSize; j++){
                    tilesValue.get(i).set(j, TilesValue.EMPTY);
                    tiles.get(i).get(j).setImage(imgEmpty);
                    log.clear();
                    logsString = "";
                }
            }
        });
    }

    private void showMessage(String message){
        logsString += dtf.format(LocalDateTime.now()) + "\t" + message + "\n";
        log.setText(logsString);
    }

    private boolean checkIfTileFilled(int x, int y, TilesValue value){
        if(x >= boardSize || y >= boardSize || x < 0 || y < 0){
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
                return true;
            }
        }
        // up or down
        if (checkIfTileFilled(x - 1, y, value) || checkIfTileFilled(x + 1, y, value)) {
            if(checkHowManyTiles(x, y, value, Course.VERTICAL) >= tilesToWin - 1) {
                return true;
            }
        }
        // up right or down left
        if (checkIfTileFilled(x - 1, y + 1, value) || checkIfTileFilled(x + 1, y - 1, value)) {
            if(checkHowManyTiles(x, y, value, Course.DIAGONAL_DOWN) >= tilesToWin - 1) {
                return true;
            }
        }
        // up left or down right
        if (checkIfTileFilled(x - 1, y - 1, value) || checkIfTileFilled(x + 1, y + 1, value)) {
            if(checkHowManyTiles(x, y, value, Course.DIAGONAL_UP) >= tilesToWin - 1) {
                return true;
            }
        }
        return false;
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
            alert.setHeaderText("Player B has won!");
        } else if(tilesValue == TilesValue.X){
            alert.setHeaderText("Player A has won!");
        }
        alert.getButtonTypes().setAll(buttonTypeOne);
        alert.showAndWait();
    }

}
