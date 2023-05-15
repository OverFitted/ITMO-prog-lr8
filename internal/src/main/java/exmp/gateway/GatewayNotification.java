package exmp.gateway;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class GatewayNotification implements Serializable {
    private final InetSocketAddress serverAddress;
    private final NotificationType type;

    public GatewayNotification(InetSocketAddress serverAddress, NotificationType type) {
        this.serverAddress = serverAddress;
        this.type = type;
    }

    public InetSocketAddress getServerAddress() {
        return serverAddress;
    }

    public NotificationType getType() {
        return type;
    }

    public enum NotificationType {
        SERVER_START, HEARTBEAT
    }
}
