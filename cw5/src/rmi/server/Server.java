package rmi.server;

import rmi.central.ICentral;
import rmi.client.Client;
import rmi.algorithms.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.List;

public class Server implements IServer {
    private int port;
    public String algorithmName;
    private String name;
    private ServerController controller;

    public Server(ServerController controller, String algorithmName, String name, int port) throws RemoteException {
        this.controller = controller;
        this.algorithmName = algorithmName;
        this.name = name;
        this.port = port;
    }

    @Override
    public synchronized List<IElement> solve(List<IElement> list, String clientId) throws InterruptedException, RemoteException {
        List<IElement> sorted;
        makeLogInCentral(name + " started sorting " + list.size() + " elements from " + clientId);
        controller.showLog("Start sorting " + list.size() + " elements from " + clientId);
        long start = System.nanoTime();
        switch(algorithmName){
            case("QuickSort"):
                QuickSort qs = new QuickSort();
                sorted = qs.solve(list);
                break;
            case("InsertSort"):
                InsertSort is = new InsertSort();
                sorted = is.solve(list);
                break;
            case("PigeonHoleSort"):
                PigeonHoleSort ps = new PigeonHoleSort();
                sorted = ps.solve(list);
                break;
            case("CountingSort"):
                CountingSort cs = new CountingSort();
                sorted = cs.solve(list);
                break;
            default:
                return null;
        }
        long elapsedTime = System.nanoTime();
        elapsedTime = elapsedTime - start;
        makeLogInCentral(name + " sorted " + list.size() + " elements in " + Client.getTime(elapsedTime) + " for " + clientId);
        controller.showLog("Sorted " + list.size() + " elements in " + Client.getTime(elapsedTime) + " for " + clientId);
        return sorted;
    }

    public void makeLogInCentral(String log) throws RemoteException {
        ICentral central = null;
        try {
            central = (ICentral) LocateRegistry.getRegistry().lookup("Centrala");
        } catch (NotBoundException | RemoteException e){
            System.err.println("Central is not running!");
        }
        central.makeLog(log);
    }
}
