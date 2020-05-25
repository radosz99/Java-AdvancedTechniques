package game;

import javafx.concurrent.Task;
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
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ExecutionException;

public class Controller implements Initializable {
    @FXML TilePane mainPanel = new TilePane();
    @FXML Button clearBtn = new Button();
    @FXML public TextArea log;
    @FXML CheckBox modeCheck = new CheckBox();
    @FXML ComboBox<String> strategyCmb = new ComboBox<>();
    @FXML ProgressBar compBar = new ProgressBar();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    final private short tilesToWin = 4;
    final private short boardSize = 5;
    private static String logsString="";
    private int turn = 0;  // X starts
    private List<List<TilesValue>> tilesValue = new ArrayList<>(boardSize);
    private Image imgEmpty = new Image(getClass().getResourceAsStream("/empty.png"));
    private Image imgX = new Image(getClass().getResourceAsStream("/x.png"));
    private Image imgO = new Image(getClass().getResourceAsStream("/o.png"));
    private Image winImg = new Image(getClass().getResourceAsStream("/win.png"));
    private List<List<ImageView>> tiles = new ArrayList<>(boardSize);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        strategyCmb.getItems().addAll("Minimax", "Alphabeta", "Random", "Simply");
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

        for(int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++) {
                int finalI = i;
                int finalJ = j;
                tiles.get(i).get(j).setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY && tilesValue.get(finalI).get(finalJ) == TilesValue.EMPTY ) {
                        if (turn == 0) {
                            tiles.get(finalI).get(finalJ).setImage(imgX);
                            tilesValue.get(finalI).set(finalJ, TilesValue.X);
                            turn = 1;
                            showMessage("Player A put X on [" + finalI + "][" + finalJ + "]");
                            if (checkIfWin(finalI, finalJ, TilesValue.X)) {
                                winAlert(TilesValue.X);
                            }
                        }
                        if (modeCheck.isSelected()) {
                            Task task = new Task<Pair<Integer, Integer>>() {
                                @Override public Pair<Integer, Integer> call() {
                                    try {
                                        for(int l = 0; l < 100; l++) {
                                            Thread.sleep(10);
                                            updateProgress(l, 100);
                                        }

                                    } catch (InterruptedException ex) {
                                        ex.printStackTrace();
                                    }
                                    String strategy = strategyCmb.getSelectionModel().getSelectedItem();
                                    SecureRandom random = new SecureRandom();
                                    int randX = 0, randY = 0;
                                    while (true) {
                                        boolean founded = false;
                                        for (List<TilesValue> lis : tilesValue) {
                                            if (lis.contains(TilesValue.EMPTY)) {
                                                founded = true;
                                                break;
                                            }
                                        }
                                        if (!founded) {
                                            randX =  Integer.MAX_VALUE;
                                            randY =  Integer.MAX_VALUE;
                                            break;
                                        }

                                        randX = random.nextInt(5);
                                        randY = random.nextInt(5);
                                        if (tilesValue.get(randX).get(randY) == TilesValue.EMPTY) {
                                            tiles.get(randX).get(randY).setImage(imgO);
                                            tilesValue.get(randX).set(randY, TilesValue.O);
                                            turn = 0;
                                            break;
                                        }
                                    }
                                    return new Pair<>(randX, randY);
                                }
                            };
                            compBar.progressProperty().bind(task.progressProperty());
                            new Thread(task).start();
                            task.setOnSucceeded(s -> {
                                Pair<Integer, Integer> result = null;
                                try {
                                    result = (Pair<Integer, Integer>) task.get();
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                } catch (ExecutionException ex) {
                                    ex.printStackTrace();
                                }
                                if (checkIfWin(result.getKey(), result.getValue(), TilesValue.O)) {
                                    winAlert(TilesValue.O);
                                }
                            });
                        }
                    } else if (e.getButton() == MouseButton.SECONDARY && !modeCheck.isSelected()) {
                        if (tilesValue.get(finalI).get(finalJ) == TilesValue.EMPTY && turn == 1) {
                            tiles.get(finalI).get(finalJ).setImage(imgO);
                            tilesValue.get(finalI).set(finalJ, TilesValue.O);
                            turn = 0;
                            showMessage("Player B put O on [" + finalI + "][" + finalJ + "]");
                            if (checkIfWin(finalI, finalJ, TilesValue.O)) {
                                winAlert(TilesValue.O);
                            }
                        }
                    }
                });
            }
        }
        //mainPanel.setTileAlignment(Pos.TOP_LEFT);
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
