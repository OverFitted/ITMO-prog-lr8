package exmp.gateway;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class GatewayMessage implements Serializable {
    private final InetSocketAddress clientAddress;
    private final Serializable payload;

    public GatewayMessage(InetSocketAddress clientAddress, Serializable payload) {
        this.clientAddress = clientAddress;
        this.payload = payload;
    }

    public InetSocketAddress getClientAddress() {
        return clientAddress;
    }

    public Serializable getPayload() {
        return payload;
    }
}