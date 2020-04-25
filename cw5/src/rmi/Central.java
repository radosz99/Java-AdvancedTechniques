package rmi;

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
    public static Registry registry;

    public Central()  {
            try {
                ICentral centrala = (ICentral) UnicastRemoteObject.exportObject(this, PortGenerator.getPort());
                registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
                registry.bind("Centrala", centrala);
            } catch (RemoteException | AlreadyBoundException e) {
                e.printStackTrace();
            }
    }

    public static void main(String[] args) throws RemoteException {
        Central central = new Central();
    }

    @Override
    public List<SimpleServer> getServers(){
        return serversList;
    }

    @Override
    public void addServer(String key, String name, int port) throws RemoteException, NotBoundException {
        if(!servers.containsKey(key)) {
            servers.put(key, 1);
        }
        else{
            servers.put(key, servers.get(key) + 1);
        }
        serversList.add(new SimpleServer(name, "fajniusi",port));
        System.out.println(servers);
        notifyClientsAboutServersQuantityChange();
    }

    @Override
    public void addClient(String name, int port) throws RemoteException{
        clients.put(name, port);
        System.out.println(clients);
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

    @Override
    public void notifyClientsAboutServersQuantityChange() throws RemoteException, NotBoundException {
        clients.forEach((k,v)-> {
            try {
                IClient client = (IClient) registry.lookup(k);
                client.updateServersList(serversList);
            } catch (RemoteException e) {
                e.printStackTrace();
            } catch (NotBoundException e) {
                e.printStackTrace();
            }
        });

    }

}
