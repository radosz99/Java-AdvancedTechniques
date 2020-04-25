package rmi;

import rmi.algorithms.IElement;
import rmi.algorithms.IntGenerator;

import java.lang.reflect.InvocationTargetException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Map;

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
        try {
            ICentral servers = (ICentral) registry.lookup("Centrala");
        } catch (NotBoundException e){
            System.err.println("Nie ma żadnej centrali");
            return null;
        }
        try {
            IServer server = (IServer) registry.lookup(serverName);
            list = server.solve(list, clientId);
            return list;
        }catch (NotBoundException e) {
            System.err.println("Nie ma takiego serwera");
            return null;
        }

    }

    @Override
    public void updateServersList(List<SimpleServer> servers){
        controller.getServersUpdate(servers);
    }
}
