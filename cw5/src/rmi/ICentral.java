package rmi;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ICentral extends Remote {
    List<SimpleServer> getServers() throws RemoteException;
    void addServer(String key, String name, int port) throws RemoteException, NotBoundException;
    void addClient(String name, int port) throws RemoteException;
    int getClientsQuantity() throws RemoteException;
    int getServersQuantity(String name) throws RemoteException;
    void notifyClientsAboutServersQuantityChange() throws RemoteException, NotBoundException;

}
