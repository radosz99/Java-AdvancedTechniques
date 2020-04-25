package rmi.client;

import rmi.server.SimpleServer;
import rmi.algorithms.IElement;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface IClient extends Remote {
    List<IElement> sort(List<IElement> list, String serverName) throws RemoteException, NotBoundException;
    void updateServersList(List<SimpleServer> servers)throws RemoteException;
}
