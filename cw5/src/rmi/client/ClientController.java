package rmi.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import rmi.ClientApplication;
import rmi.central.ICentral;
import rmi.algorithms.PortGenerator;
import rmi.server.SimpleServer;
import rmi.algorithms.IElement;
import rmi.algorithms.IntGenerator;

import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class ClientController implements Initializable {
    @FXML private Button sort;
    @FXML private Button generateRandom;
    private IClient client;
    private static ObservableList<SimpleServer> serversList;
    private static ObservableList<IElement> numbers;
    public ObservableList<String> dataTypeList = FXCollections.observableArrayList();
    @FXML private TextField quantity;
    @FXML private TextField min;
    @FXML private TextField max;
    @FXML private Label quantityLbl;
    @FXML private Label minLbl;
    @FXML private Label maxLbl;
    @FXML private Label dataTypeLbl;
    @FXML private Label serverLbl;
    @FXML private Label mainLbl;
    @FXML private Label portLbl;
    @FXML private ComboBox <String> dataType;
    @FXML private TableColumn<SimpleServer, String> column0;
    @FXML private TableColumn<SimpleServer, String> column1;
    @FXML private TableColumn<SimpleServer, String> column2;
    @FXML private TableColumn<SimpleServer, Integer> column3;
    @FXML private TableColumn<IElement, String> column4;
    @FXML private TableColumn<IElement, Float> column5;
    @FXML private TableView<SimpleServer> tableViewServers = new TableView();
    @FXML private TableView<IElement> tableViewNumbers = new TableView();
    @FXML private AnchorPane ap;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sort.setText("Sort");
        generateRandom.setText("Generate");
        try {
            Registry registry = LocateRegistry.getRegistry();
            ICentral central = (ICentral) registry.lookup("Centrala");
            int port = PortGenerator.getPort();
            String clientId = "Client#"+central.getClientsQuantity();
            mainLbl.setText(clientId);
            portLbl.setText("Port " + port);
            serversList = FXCollections.observableArrayList(central.getServers());
            client = (IClient) UnicastRemoteObject.exportObject(new Client(this, clientId), port);
            registry.bind(clientId, client);
            central.addClient(clientId, port);
        } catch (AlreadyBoundException | NotBoundException | RemoteException ex) {
            badAlert("Central is not running and client is not connected! (Run Central.java)");
        }

        column0 = new TableColumn<>("Data type");
        column0.setCellValueFactory(new PropertyValueFactory<>("dataType"));
        column1 = new TableColumn<>("Server name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column2 = new TableColumn<>("Description");
        column2.setCellValueFactory(new PropertyValueFactory<>("description"));
        column3 = new TableColumn<>("Port number");
        column3.setCellValueFactory(new PropertyValueFactory<>("port"));
        column4 = new TableColumn<>("Key");
        column4.setCellValueFactory(new PropertyValueFactory<>("word"));
        column5 = new TableColumn<>("Value");
        column5.setCellValueFactory(new PropertyValueFactory<>("value"));
        column0.setSortable(false);
        column1.setSortable(false);
        column2.setSortable(false);
        column3.setSortable(false);
        column4.setSortable(false);
        column5.setSortable(false);
        tableViewNumbers.getColumns().addAll(column4,column5);
        tableViewServers.getColumns().addAll(column1, column2, column3, column0);
        tableViewServers.setItems(serversList);
        tableViewNumbers.setItems(numbers);
        tableViewServers.setPlaceholder(new Label("No servers available!"));
        tableViewNumbers.setPlaceholder(new Label("Generate some data!"));
        tableViewServers.setEditable(false);
        tableViewNumbers.setEditable(false);
        tableViewNumbers.setScaleShape(false);

        dataTypeList.addAll("integer", "float");
        dataType.setItems(dataTypeList);

    }

    public void generateRandomData(){
        try {
            numbers = FXCollections.observableArrayList(IntGenerator.getIntData(Integer.parseInt(quantity.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText()), dataType.getSelectionModel().getSelectedItem()));
        }catch (IllegalArgumentException | NullPointerException e){
            badAlert("Wrong format!");
        }
        tableViewNumbers.setItems(numbers);
    }
    public void sort() throws RemoteException {
        if(tableViewServers.getItems().size()==0){
            badAlert("Currently no active servers!");
            return;
        }
        SimpleServer server = tableViewServers.getSelectionModel().getSelectedItem();
        if(server==null){
            if(tableViewServers.getItems()!=null) {
                badAlert("Choose server!");
            }
            else{
                badAlert("There is no connection to Central (Run Central.java first)!");
            }
            return;
        }

        try {
            if (dataType.getSelectionModel().getSelectedItem().equals("float") && (server.getDescription().equals("CountingSort") || server.getDescription().equals("PigeonHoleSort"))) {
                badAlert("The server is unable to sort this type of data");
                return;
            }
        } catch (NullPointerException x){
            badAlert("There is no data");
            return;
        }

        sort.setDisable(true);
        generateRandom.setDisable(true);

        new Thread(() -> {
            ObservableList<IElement> result = null;
            try {
                result = FXCollections.observableArrayList(client.sort(new ArrayList<>(numbers), server.getName()));
            } catch (RemoteException | NotBoundException | NullPointerException x) {
                Thread.interrupted();
            }
            if(result!=null) {
                numbers=result;
                tableViewNumbers.setItems(numbers);
            }
            sort.setDisable(false);
            generateRandom.setDisable(false);
        }).start();
    }


    public void getServersUpdate(List<SimpleServer> servers){
        serversList= FXCollections.observableArrayList(servers);
        tableViewServers.setItems(serversList);
    }

    private static void badAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

}
