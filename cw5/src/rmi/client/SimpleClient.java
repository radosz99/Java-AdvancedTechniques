package rmi.client;

import java.util.Objects;

public class SimpleClient {
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    String clientId;

    public int getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleClient that = (SimpleClient) o;
        return portNumber == that.portNumber &&
                Objects.equals(clientId, that.clientId);
    }

    public SimpleClient(String clientId, int portNumber) {
        this.clientId = clientId;
        this.portNumber = portNumber;
    }

    int portNumber;

}
