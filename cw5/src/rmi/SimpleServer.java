package rmi;

import java.io.Serializable;

public class SimpleServer implements Serializable {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public SimpleServer(String name, String description, int port) {
        this.name = name;
        this.description = description;
        this.port =port;
    }

    @Override
    public String toString() {
        return "SimpleServer{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", port=" + port +
                '}';
    }

    private String name;
    private String description;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private int port;

}
