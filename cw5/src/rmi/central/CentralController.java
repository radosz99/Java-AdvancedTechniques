package rmi.central;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import rmi.client.SimpleClient;
import rmi.server.SimpleServer;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CentralController implements Initializable {
    ICentral central;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    @FXML private TableView<SimpleClient> clients = new TableView();
    @FXML private TableView<SimpleServer> servers = new TableView();
    private List<SimpleClient> clientsList = new ArrayList<>();
    private List<SimpleServer> serversList = new ArrayList<>();
    @FXML private TableColumn<SimpleServer, String> column1;
    @FXML private TableColumn<SimpleServer, Integer> column3;
    @FXML private TableColumn<SimpleClient, String> column4;
    @FXML private TableColumn<SimpleClient, Integer> column5;
    @FXML private TextArea logs;
    private static String logsString="";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Registry registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Central central = new Central(this);

        column1 = new TableColumn<>("Server name");
        column1.setCellValueFactory(new PropertyValueFactory<>("name"));
        column3 = new TableColumn<>("Port number");
        column3.setCellValueFactory(new PropertyValueFactory<>("port"));
        column4 = new TableColumn<>("Client ID");
        column4.setCellValueFactory(new PropertyValueFactory<>("clientId"));
        column5 = new TableColumn<>("Port number");
        column5.setCellValueFactory(new PropertyValueFactory<>("portNumber"));

        column1.setSortable(false);
        column3.setSortable(false);
        column4.setSortable(false);
        column5.setSortable(false);
        clients.getColumns().addAll(column4,column5);
        servers.getColumns().addAll(column1, column3);
        servers.setPlaceholder(new Label("No servers"));
        clients.setPlaceholder(new Label("No clients"));
    }

    public void addClient(String clientId, int port){
        clientsList.add(new SimpleClient(clientId, port));
        clients.setItems(FXCollections.observableList(clientsList));
    }

    public void addServer(SimpleServer newServer){
        serversList.add(newServer);
        servers.setItems(FXCollections.observableArrayList(serversList));
    }

    public void removeServer(SimpleServer deletedServer){
        serversList.remove(deletedServer);
        servers.setItems(FXCollections.observableArrayList(serversList));
    }

    public void clearLogs(){
        logsString="";
        logs.setText(logsString);
    }

    public void showLog(String message){
        logsString+=dtf.format(LocalDateTime.now()) + "\t" + message+"\n";
        logs.setText(logsString);
    }

}
