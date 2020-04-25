package rmi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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
    @FXML private TextField quantity;
    @FXML private TextField min;
    @FXML private TextField max;
    @FXML private Label quantityLbl;
    @FXML private Label minLbl;
    @FXML private Label maxLbl;
    @FXML private Label mainLbl;
    @FXML private TableColumn<SimpleServer, String> column1;
    @FXML private TableColumn<SimpleServer, String> column2;
    @FXML private TableColumn<SimpleServer, Integer> column3;
    @FXML private TableColumn<IElement, String> column4;
    @FXML private TableColumn<IElement, Float> column5;
    @FXML private TableView<SimpleServer> tableViewServers = new TableView();
    @FXML private TableView<IElement> tableViewNumbers = new TableView();

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
            serversList = FXCollections.observableArrayList(central.getServers());
            client = (IClient) UnicastRemoteObject.exportObject(new Client(this, clientId), port);
            registry.bind(clientId, client);
            central.addClient(clientId, port);
            System.err.println("Client ready");
        } catch (AlreadyBoundException | NotBoundException | RemoteException ex) {
            System.err.println("Run central first!");
        }

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
        column1.setSortable(false);
        column2.setSortable(false);
        column3.setSortable(false);
        column4.setSortable(false);
        column5.setSortable(false);
        tableViewNumbers.getColumns().addAll(column4,column5);
        tableViewServers.getColumns().addAll(column1, column2, column3);
        tableViewServers.setItems(serversList);
        tableViewNumbers.setItems(numbers);
        tableViewServers.setPlaceholder(new Label("No servers available!"));
        tableViewNumbers.setPlaceholder(new Label("Generate some data!"));
        tableViewServers.setEditable(false);
        tableViewNumbers.setEditable(false);
        tableViewNumbers.setScaleShape(false);

    }

    public void generateRandomData(){
        try {
            numbers = FXCollections.observableArrayList(IntGenerator.getIntData(Integer.parseInt(quantity.getText()), Integer.parseInt(min.getText()), Integer.parseInt(max.getText())));
        }catch (NumberFormatException e){
            System.err.println("Zły format!");
        }
        tableViewNumbers.setItems(numbers);
    }
    public void sort() throws RemoteException {
        try {
            numbers = FXCollections.observableArrayList(client.sort(new ArrayList<IElement>(numbers), tableViewServers.getSelectionModel().getSelectedItem().getName()));
            tableViewNumbers.setItems(numbers);
        }
        catch (NotBoundException e){
            System.err.println("Nie ma takiego serwera");
        }
        catch (NullPointerException n){
            System.err.println("Wybierz serwer!");
        }
    }

    public void getServersUpdate(List<SimpleServer> servers){
        serversList= FXCollections.observableArrayList(servers);
        tableViewServers.setItems(serversList);
    }

}
