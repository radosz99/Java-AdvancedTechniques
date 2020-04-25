package rmi.client;

import rmi.central.ICentral;
import rmi.server.SimpleServer;
import rmi.algorithms.IElement;
import rmi.server.IServer;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class Client implements IClient {
    ClientController controller;
    String clientId;
    public Client(ClientController controller, String clientId){
        this.clientId = clientId;
        this.controller=controller;
    }

    @Override
    public List<IElement>  sort(List<IElement>list, String serverName) throws RemoteException {
        Registry registry = LocateRegistry.getRegistry();
        ICentral central;
        try {
            central = (ICentral) registry.lookup("Centrala");
        } catch (NotBoundException e){
            System.err.println("Central is not running!");
            return null;
        }
        try {
            IServer server = (IServer) registry.lookup(serverName);
            long start = System.nanoTime();
            list = server.solve(list, clientId);
            long elapsedTime = System.nanoTime();
            elapsedTime = elapsedTime - start;
            central.makeLog(clientId+ " waited " + getTime(elapsedTime) + " for sorting");
            return list;
        } catch (NotBoundException | InterruptedException e) {
            System.err.println("Server does not exist");
            return null;
        }
    }

    @Override
    public void updateServersList(List<SimpleServer> servers){
        controller.getServersUpdate(servers);
    }

    public static String getTime(long elapsedTime){
        if (elapsedTime>1000000000) {
            return round((float)elapsedTime/1000000000,1) + " s";
        }
        else if(elapsedTime>1000000)
            return round((float)elapsedTime/1000000,1) + " ms";
        else if(elapsedTime>1000)
            return round((float)elapsedTime/1000,1) + " μs";
        else
            return round((float)elapsedTime,1) + " ns";
    }

    @SuppressWarnings("deprecation")
    private static float round(float d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Float.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);

        return bd.floatValue();
    }

}
