package exmp.gateway;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class GatewayNotification implements Serializable {
    private final InetSocketAddress serverAddress;

    public GatewayNotification(InetSocketAddress serverAddress) {
        this.serverAddress = serverAddress;
    }

    public InetSocketAddress getServerAddress() {
        return serverAddress;
    }
}