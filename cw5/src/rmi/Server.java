package rmi;

import rmi.algorithms.*;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Server implements IServer {
    private int port=0;
    private Registry registry = LocateRegistry.getRegistry();
    public String algorithmName = "QuickSort";

    public Server() throws RemoteException {
        try {
            ICentral list = (ICentral) registry.lookup("Centrala");
            port = PortGenerator.getPort();
            String binding_name = algorithmName+"#"+list.getServersQuantity(algorithmName);
            list.addServer(algorithmName, binding_name,port);
            IServer server = (IServer) UnicastRemoteObject.exportObject(this, port);
            registry.bind(binding_name, server);
            System.err.println("Server ready");
            } catch (AlreadyBoundException | NotBoundException ex) {
            System.err.println("Server exception: " + ex.toString());
        }
    }

    public static void main(String[] args) throws RemoteException {
        Server server = new Server();
    }

    @Override
    public List<IElement> solve(List<IElement> list, String clientId) {
        switch(algorithmName){
            case("QuickSort"):
                QuickSort qs = new QuickSort();
                System.err.println("Sorted " + list.size() + " elements from " + clientId);
                return qs.solve(list);
            case("InsertSort"):
                InsertSort is = new InsertSort();
                System.err.println("Sorted " + list.size() + " elements from " + clientId);
                return is.solve(list);
            case("PigeonHoleSort"):
                PigeonHoleSort ps = new PigeonHoleSort();
                System.err.println("Sorted " + list.size() + " elements from " + clientId);
                return ps.solve(list);
            case("CountingSort"):
                CountingSort cs = new CountingSort();
                System.err.println("Sorted " + list.size() + " elements from " + clientId);
                return cs.solve(list);
            default:
                return null;
        }
    }
}
