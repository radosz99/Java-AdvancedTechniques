package rmi.server;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import rmi.central.ICentral;
import rmi.algorithms.PortGenerator;

import java.net.URL;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ServerController implements Initializable {
    private IServer server;
    private String algorithmName = "QuickSort";
    private SimpleServer serv;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    @FXML private Label portLbl;
    @FXML private Label mainLbl;
    @FXML private Button clearBtn;
    @FXML private Button registerBtn;
    @FXML private Button unregisterBtn;
    @FXML private TextArea logs;
    private static String logsString="";
    ICentral central;
    Registry registry;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            registry = LocateRegistry.getRegistry();
            central = (ICentral) registry.lookup("Centrala");
            int port = PortGenerator.getPort();
            String bindingName = algorithmName+"#"+central.getServersQuantity(algorithmName);
            portLbl.setText("Port " + port);
            mainLbl.setText(bindingName);
            serv = new SimpleServer(bindingName, algorithmName, port);
            server = (IServer) UnicastRemoteObject.exportObject(new Server(this, algorithmName, bindingName, port), port);
            if(central.register(serv)) {
                registry.bind(bindingName, server);
                showLog("Server ready");
            }
            else{
                badAlert("Server is not able to connect to Central");
            }
        } catch (AlreadyBoundException | NotBoundException | RemoteException ex) {
            badAlert("Central is not running and server is not connected! (Run Central.java)");
        }
        registerBtn.setDisable(true);
        logs.setEditable(false);
    }

    public void clearLogs(){
        logsString="";
        logs.setText(logsString);
    }

    public void registerServer() throws RemoteException, NotBoundException {
        central.register(serv);
        registerBtn.setDisable(true);
        unregisterBtn.setDisable(false);
        showLog("Server registered");
    }


    public void unregisterServer() throws RemoteException, NotBoundException {
        if(serv!=null) {
            central.unregister(serv);
            unregisterBtn.setDisable(true);
            registerBtn.setDisable(false);
            showLog("Server unregistered");
        }
        else{
            badAlert("No server registered");
        }
    }


    private static void badAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void showLog(String message){
        logsString+=dtf.format(LocalDateTime.now()) + "\t" + message+"\n";
        logs.setText(logsString);
    }
}
