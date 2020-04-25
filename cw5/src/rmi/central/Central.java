package rmi.central;

import rmi.algorithms.PortGenerator;
import rmi.client.IClient;
import rmi.server.SimpleServer;

import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.HashMap;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class Central implements ICentral {
    public static Map<String,Integer> servers = new HashMap<>();
    public static List<SimpleServer> serversList = new ArrayList<>();
    public static Map<String,Integer> clients = new HashMap<>();
    private CentralController controller;

    public Central(CentralController controller)  {
        this.controller=controller;
        try {
            ICentral centrala = (ICentral) UnicastRemoteObject.exportObject(this, PortGenerator.getPort());
            LocateRegistry.getRegistry().bind("Centrala", centrala);
        } catch (RemoteException | AlreadyBoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<SimpleServer> getServers(){
        return serversList;
    }

    @Override
    public boolean addServer(SimpleServer server) throws RemoteException, NotBoundException {
        for(SimpleServer ser : serversList){
            if(ser.getPort()==server.getPort()){
                System.err.println("Server already exist");
                return false;
            }
        }
        if(!servers.containsKey(server.getDescription())) {
            servers.put(server.getDescription(), 1);
        }
        else{
            servers.put(server.getDescription(), servers.get(server.getDescription()) + 1);
        }
        serversList.add(server);
        controller.showLog(server.getName() + " registered");
        controller.addServer(server);
        notifyClientsAboutServersQuantityChange();
        return true;
    }

    @Override
    public boolean register(SimpleServer server) throws RemoteException, NotBoundException {
        return addServer(server);
    }

    @Override
    public void unregister(SimpleServer server) throws RemoteException, NotBoundException{
        for (SimpleServer serv : serversList){
            if(server.equals(serv)){
                serversList.remove(serv);
                controller.removeServer(server);
                controller.showLog(server.getName() + " unregistered");
                notifyClientsAboutServersQuantityChange();
                break;
            }
        }
    }

    @Override
    public void addClient(String clientId, int port) throws RemoteException{
        clients.put(clientId, port);
        controller.showLog(clientId + " has been detected!");
        controller.addClient(clientId,port);
    }

    @Override
    public int getClientsQuantity() throws RemoteException{
        return clients.size();
    }

    @Override
    public int getServersQuantity(String name) throws RemoteException{
        if(servers.containsKey(name)) {
            return servers.get(name);
        }
        else{
            return 0;
        }
    }

    public void notifyClientsAboutServersQuantityChange() {
        clients.forEach((k,v)-> {
            try {
                IClient client = (IClient) LocateRegistry.getRegistry().lookup(k);
                client.updateServersList(serversList);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void makeLog(String log) throws RemoteException{
        controller.showLog(log);
    }

}
