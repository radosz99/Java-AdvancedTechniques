package game;

import game.algorithm.Handler;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.Pair;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller implements Initializable {
    @FXML TilePane mainPanel = new TilePane();
    @FXML Button clearBtn = new Button();
    @FXML public TextArea log;
    @FXML ComboBox<String> strategyCmb = new ComboBox<>();
    @FXML ComboBox<String> langCmb = new ComboBox<>();
    @FXML Label timeLbl = new Label();
    Timeline clock = new Timeline();
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    final private short tilesToWin = 4;
    final private short boardSize = 5;
    final private short tilesShift = 9;
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
                strategyCmb.getItems().addAll("Random", "Greedy", "Minimax");
            }
            else {
                strategyCmb.getItems().clear();
                strategyCmb.getItems().addAll("Random", "Greedy");
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
                            return;
                        }
                        boolean emptyFieldExists = false;
                        for(List<TilesValue> row : tilesValue) {
                            if (row.contains(TilesValue.EMPTY)) {
                                emptyFieldExists = true;
                                break;
                            }
                        }
                        if(!emptyFieldExists){
                            drawAlert();
                            return;
                        }

                        String strategy = strategyCmb.getSelectionModel().getSelectedItem();
                        String lang = langCmb.getSelectionModel().getSelectedItem();
                        Task task = new Task<Pair<Integer, Integer>>() {
                            @Override public Pair<Integer, Integer> call() {
                                Pair<Integer, Integer> coords = null;
                                try{
                                    Handler handler = new Handler();
                                    coords = handler.makeMove(strategy, lang, tilesValue);
                                } catch (IndexOutOfBoundsException e){
                                    e.printStackTrace();
                                }
                                return coords;
                            }
                        };

                        new Thread(task).start();

                        AtomicInteger x = new AtomicInteger();
                        AtomicInteger y = new AtomicInteger();
                        task.setOnSucceeded(s -> {
                            Pair<Integer, Integer> coords = null;
                            try {
                                coords = (Pair<Integer, Integer>) task.get();
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            } catch (ExecutionException ex) {
                                ex.printStackTrace();
                            }
                            x.set(coords.getKey());
                            y.set(coords.getValue());


                        if(x.get() >= boardSize || y.get() >= boardSize){
                            return;
                        }

                        tiles.get(x.get()).get(y.get()).setImage(imgO);
                        tilesValue.get(x.get()).set(y.get(), TilesValue.O);
                        showMessage("Computer put O on [" + x + "][" + y + "]");
                        if (checkIfWin(x.get(), y.get(), TilesValue.O)) {
                            winAlert(TilesValue.O);
                        }
                        });
                    }
                });
            }
        }
        mainPanel.setHgap(tilesShift);
        mainPanel.setVgap(tilesShift);

        clearBtn.setOnMouseClicked( e -> {
            for(int i = 0; i < boardSize; i++){
                clearBoard();
            }
        });

        DateTimeFormatter localeFormat = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM);
        clock.stop();
        clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            timeLbl.setText(LocalDateTime.now().format(localeFormat));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    private void showMessage(String message){
        logsString += dtf.format(LocalDateTime.now()) + "\t" + message + "\n";
        log.setText(logsString);
    }

    private void clearBoard(){
        for(int i = 0; i < boardSize; i++){
            for (int j = 0; j < boardSize; j++){
                tilesValue.get(i).set(j, TilesValue.EMPTY);
                tiles.get(i).get(j).setImage(imgEmpty);
                log.clear();
                logsString = "";
            }
        }
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
        clearBoard();
    }

    private void drawAlert() {
        ButtonType buttonTypeOne = new ButtonType("Ok");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("End of the game");
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        alert.setHeaderText("Draw!");
        alert.getButtonTypes().setAll(buttonTypeOne);
        alert.showAndWait();
        clearBoard();
    }

}
