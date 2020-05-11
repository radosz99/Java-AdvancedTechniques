package sockets;

import javax.xml.soap.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Listener extends Thread{
    public Socket socketAccept;
    public static Socket socket;
    public static ServerSocket serverSocket;
    public static String port;
    public static String nextPort;
    private BufferedReader in;
    public static Controller controller;

    public Listener(String port) {
        this.nextPort = String.valueOf(Integer.valueOf(port)+1);
        this.port = port;
    }

    public void run()
    {
        try {
            serverSocket = new ServerSocket(Integer.parseInt(port));
            while (true) {
                socketAccept = serverSocket.accept();
                in = new BufferedReader(new InputStreamReader(socketAccept.getInputStream()));
                if (!socketAccept.isClosed()) {
                    String msg, response;
                    response = in.readLine();

                    while ((msg = in.readLine()) != null) {
                        response += msg;
                    }
                    if (response != null) {
                        convertMessage(response);
                    }
                }
                in.close();
            }
        } catch (IOException | SOAPException e) {
            e.printStackTrace();
        }
    }
    public static void send(SOAPMessage soapMessage) throws SOAPException, IOException
    {
        socket = new Socket("127.0.0.1", Integer.parseInt(nextPort));
        PrintStream out = new PrintStream(socket.getOutputStream(), true);
        soapMessage.writeTo(out);
        out.close();
    }

    public static int getServerPort(){
        return serverSocket.getLocalPort();
    }


    public void convertMessage(String message) throws SOAPException, IOException {
        SoapMessageHandler handl = new SoapMessageHandler();
        handl.deserializeMessage(message);
        String receiver = handl.getReceiver();
        String sender = handl.getSender();
        String msg = handl.getMessage();
        String type = handl.getType();

        if(receiver.equals(port))
        {
            controller.receive.setText(msg);
            controller.showLog("Otrzymano wiadomość od " + sender);
            controller.showMessage(sender + ": " + msg);
        }  else if (!sender.equals(port)) {
            send(handl.getSoapMessage());
            controller.showLog("Przekazano wiadomość od " + sender + " do " + receiver);
        }
         if (type.equals("Broadcast") && !sender.equals(port)) {
                controller.receive.setText(msg);
                controller.showLog("Otrzymano wiadomość od " + sender);
                controller.showMessage(sender + ": " + msg);
                if(!nextPort.equals(sender)) {
                    send(handl.getSoapMessage());
                }
            }
    }

}
