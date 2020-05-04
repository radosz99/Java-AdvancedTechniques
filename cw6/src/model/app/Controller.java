package model.app;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import model.app.entities.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller implements Initializable{
    List<Trip> trips = new ArrayList<>();
    @FXML private Button actButton;
    @FXML private Button partButton;
    @FXML private Button importCyclistButton;
    @FXML private Button importTripButton;
    @FXML private Button changeIdButton;
    @FXML private Button deleteCyclistButton;
    @FXML private Button changeCyclistIdButton;
    @FXML private TextField oldCyclistIdTextField;
    @FXML private TextField newCyclistIdTextField;
    @FXML private TextField nameTextField;
    @FXML private TextField surnameTextField;
    @FXML private TextField dateTextField;
    @FXML private TableView<Trip> tripsView = new TableView();
    @FXML private TableView<Cyclist> cyclistsView = new TableView();
    @FXML private TableView<Cyclist> participantsView = new TableView();
    @FXML private TableColumn<Trip, Integer> column1;
    @FXML private TableColumn<Trip, Integer> column2;
    @FXML private TableColumn<Trip, String> column3;
    @FXML private TableColumn<Trip, Date> column4;
    @FXML private TableColumn<Cyclist, Integer> column5;
    @FXML private TableColumn<Cyclist, String> column6;
    @FXML private TableColumn<Cyclist, String> column7;
    @FXML private TableColumn<Cyclist, Date> column8;
    @FXML private TableColumn<Cyclist, Integer> column9;
    @FXML private TableColumn<Cyclist, String> column10;
    @FXML private TableColumn<Cyclist, String> column11;
    @FXML private TableColumn<Cyclist, Date> column12;
    @FXML private TextField oldTripId;
    @FXML private TextField newTripId;
    private CyclistDao cyclistDao;
    private ParticipantDao participantDao;
    private TripDao tripDao;
    List<Cyclist> cyclists = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        oldCyclistIdTextField.setPromptText("stare");
        oldTripId.setPromptText("stare");
        newCyclistIdTextField.setPromptText("nowe");
        newTripId.setPromptText("nowe");
        cyclistDao = null;
        tripDao = null;
        column1 = new TableColumn<>("Id");
        column1.setCellValueFactory(new PropertyValueFactory<>("id"));
        column2 = new TableColumn<>("Autor");
        column2.setCellValueFactory(new PropertyValueFactory<>("authorId"));
        column3 = new TableColumn<>("Nazwa");
        column3.setCellValueFactory(new PropertyValueFactory<>("name"));
        column4 = new TableColumn<>("Data");
        column4.setCellValueFactory(new PropertyValueFactory<>("date"));
        column5 = new TableColumn<>("Id");
        column5.setCellValueFactory(new PropertyValueFactory<>("id"));
        column6 = new TableColumn<>("Imię");
        column6.setCellValueFactory(new PropertyValueFactory<>("name"));
        column7 = new TableColumn<>("Nazwisko");
        column7.setCellValueFactory(new PropertyValueFactory<>("surname"));
        column8 = new TableColumn<>("Data urodzenia");
        column8.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        column9 = new TableColumn<>("Id");
        column9.setCellValueFactory(new PropertyValueFactory<>("id"));
        column10 = new TableColumn<>("Imię");
        column10.setCellValueFactory(new PropertyValueFactory<>("name"));
        column11 = new TableColumn<>("Nazwisko");
        column11.setCellValueFactory(new PropertyValueFactory<>("surname"));
        column12 = new TableColumn<>("Data urodzenia");
        column12.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth"));
        tripsView.getColumns().addAll(column1, column2, column3,column4);
        cyclistsView.getColumns().addAll(column5, column6, column7,column8);
        participantsView.getColumns().addAll(column9, column10, column11,column12);
        surnameTextField.setPromptText("Nazwisko");
        nameTextField.setPromptText("Imię");
        dateTextField.setPromptText("Data yyyy-mm-dd");
    }

    public void show(){
        try {
            cyclistDao = new CyclistDao(this);
            tripDao = new TripDao(this);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

        try {
            trips = tripDao.getAllTrips();
            cyclists = cyclistDao.getAllCyclists();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tripsView.setItems(FXCollections.observableList(trips));
        cyclistsView.setItems(FXCollections.observableList(cyclists));
        goodAlert("Zaktualizowano!");

    }

    public void exportCyclists(){
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showSaveDialog(null);
        if(selectedFile==null) {
            return;
        }
        CyclistList cyclistList = new CyclistList();
        cyclistList.setCyclists(cyclists);

        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(CyclistList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(cyclistList,selectedFile);

            Unmarshaller unmarshaller = context.createUnmarshaller();
            CyclistList cycs  = (CyclistList) unmarshaller.unmarshal(selectedFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void exportTrips(){
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showSaveDialog(null);
        if(selectedFile==null) {
            return;
        }
        TripsList tripsList = new TripsList();
        tripsList.setTrips(trips);

        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(TripsList.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(tripsList,selectedFile);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void importCyclists() throws IOException, SQLException {
        CyclistDao cyclistDao = new CyclistDao(this);
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile==null) {
            return;
        }

        JAXBContext context = null;
        CyclistList cycs = null;
        try {
            context = JAXBContext.newInstance(CyclistList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            cycs  = (CyclistList) unmarshaller.unmarshal(selectedFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        importCyclistButton.setDisable(true);
        CyclistList finalCycs = cycs;

        new Thread(() -> {
            AtomicInteger counter= new AtomicInteger();
            for(Cyclist cyclist : finalCycs.getCyclists()){
                if(cyclistDao.insertCyclist(cyclist.getName(), cyclist.getSurname(), cyclist.getDateOfBirth())){
                    counter.getAndIncrement();
                }
            }
            importCyclistButton.setDisable(false);
        }).start();

    }

    public void importTrips() throws IOException, SQLException {
        TripDao tripDao = new TripDao(this);
        FileChooser fc = new FileChooser();
        File selectedFile = fc.showOpenDialog(null);
        if(selectedFile==null) {
            return;
        }

        JAXBContext context = null;
        TripsList trs = null;
        try {
            context = JAXBContext.newInstance(TripsList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            trs  = (TripsList) unmarshaller.unmarshal(selectedFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        importTripButton.setDisable(true);
        TripsList finalTrs = trs;

        new Thread(() -> {
            AtomicInteger counter= new AtomicInteger();
            for(Trip trip : finalTrs.getTrips()){
                if(tripDao.insertTrip(trip.getName(), trip.getAuthorId(), trip.getDate())){
                    counter.getAndIncrement();
                }
            }
            importTripButton.setDisable(false);
        }).start();

    }



    public void showParticipants() throws IOException, SQLException {
        participantDao = new ParticipantDao(this);
        cyclistDao = new CyclistDao(this);
        List<Cyclist> cyclists = new ArrayList<>();
        List<Participant> participants = new ArrayList<>();
        try {
            participants = participantDao.getAllParticipants(tripsView.getSelectionModel().getSelectedItem().getId());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException n){
            badAlert("Wybierz wycieczkę!");
        }

        for (Participant participant : participants){
            cyclists.add(cyclistDao.getCyclistById(participant.getCyclist_id()));
        }

        participantsView.setItems(FXCollections.observableList(cyclists));

    }

    public void changeTripId() throws IOException, SQLException {
        tripDao = new TripDao(this);
        try {
            tripDao.changeTripId(Integer.parseInt(oldTripId.getText()), Integer.parseInt(newTripId.getText()));
        } catch (NumberFormatException n){
            warningAlert("Zły format!");
        }
    }

    public void changeCyclistId() throws IOException, SQLException {
        cyclistDao = new CyclistDao(this);
        try {
            cyclistDao.changeCyclistId(Integer.parseInt(oldCyclistIdTextField.getText()), Integer.parseInt(newCyclistIdTextField.getText()));
        } catch (NumberFormatException n){
            warningAlert("Zły format!");
        }
    }
    public void deleteCyclist() throws IOException, SQLException {
        cyclistDao = new CyclistDao(this);
        try {
            cyclistDao.deleteCyclistById(cyclistsView.getSelectionModel().getSelectedItem().getId());
        }   catch (NullPointerException n){
            badAlert("Wybierz kolarza!");
        }
    }

    public void addCyclist() throws IOException, SQLException {
        cyclistDao = new CyclistDao(this);
        try {
            if(cyclistDao.insertCyclist(nameTextField.getText(), surnameTextField.getText(), parseDate(dateTextField.getText()))){
                goodAlert("Pomyślnie dodano do bazy");
            }
            else{
                badAlert("Błąd przy dodawaniu do bazy!");
            }
        } catch (NumberFormatException n){
            warningAlert(n.getMessage());
        }
    }

    public void addParticipant() throws IOException, SQLException {
        participantDao = new ParticipantDao(this);
        participantDao.insertParticipant(cyclistsView.getSelectionModel().getSelectedItem().getId(), tripsView.getSelectionModel().getSelectedItem().getId());

    }
    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
    public void badAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void warningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Ostrzeżenie");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public static void goodAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Sukces");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

}
