package rmi.central;

import rmi.server.SimpleServer;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ICentral extends Remote {
    List<SimpleServer> getServers() throws RemoteException;
    boolean addServer(SimpleServer server) throws RemoteException, NotBoundException;
    boolean register(SimpleServer server) throws RemoteException, NotBoundException;
    void addClient(String name, int port) throws RemoteException;
    int getClientsQuantity() throws RemoteException;
    int getServersQuantity(String name) throws RemoteException;
    void unregister(SimpleServer server) throws RemoteException, NotBoundException;
    void makeLog(String log) throws RemoteException;

}
