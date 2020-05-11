package sockets;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML public TextField receiver;
    @FXML public TextField message;
    @FXML public TextArea receive;
    @FXML public TextArea log;
    @FXML public Button sendBtn;
    @FXML public Label title;
    @FXML public ComboBox<String> typeCombo;
    private static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    private static String logsString="";
    private static String msgString="";


    public void send() {
        if(receiver.getText().isEmpty()){
            badAlert("Wpisz adresata!");
            return;
        }
        if(message.getText().isEmpty()){
            badAlert("Wiadomość jest pusta!");
            return;
        }
        try {
            String type = typeCombo.getSelectionModel().getSelectedItem();
            SOAPMessage soapMsg = SoapMessageHandler.serializeMessage(message.getText(),receiver.getText(), String.valueOf(Listener.port), type);
            Listener.send(soapMsg);
            if(type.equals("Unicast")) {
                showLog("Przesłano wiadomość do " + receiver.getText());
            }
            else if(type.equals("Broadcast")){
                showLog("Przesłano wiadomość typu broadcast");
            }
        } catch (SOAPException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException n){
            badAlert("Wybierz typ przesyłu");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        title.setText("Port: " + Listener.getServerPort());
        Listener.controller = this;
        receive.setEditable(false);
        typeCombo.getItems().add("Broadcast");
        typeCombo.getItems().add("Unicast");
    }

    public void clearLogs(){
        logsString="";
        log.setText(logsString);
    }

    public void showLog(String message){
        logsString+=dtf.format(LocalDateTime.now()) + "\t" + message+"\n";
        log.setText(logsString);
    }

    private static void badAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    private static void goodAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setHeaderText(message);
        alert.showAndWait();
    }

    public void closeRing(){
        if(PortGenerator.available(Integer.valueOf(Listener.nextPort))){
            Listener.nextPort = "8100";
            goodAlert("Poprawnie połączono w pierścień");
        } else{
            badAlert("To nie jest ostatni uruchomiony słuchacz!");
        }
    }

    public void showMessage(String message){
        msgString+=dtf.format(LocalDateTime.now()) + "\t" + message + "\n";
        receive.setText(msgString);
    }

}
