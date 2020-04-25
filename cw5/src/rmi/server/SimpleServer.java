package rmi.server;

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
        if(description.equals("QuickSort") || description.equals("InsertSort")){
            dataType="all";
        }
        else{
            dataType="integer";
        }
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleServer that = (SimpleServer) o;
        return port == that.port &&
                name.equals(that.name) &&
                description.equals(that.description);
    }


    private String description;

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    private String dataType;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    private int port;

}
