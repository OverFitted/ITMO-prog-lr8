package exmp;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {
    private final int port;
    private final Map<Long, InetSocketAddress> clientAddresses = new ConcurrentHashMap<>();

    public ChatServer(int port) {
        this.port = port;
    }

    public void start() {
        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.bind(new InetSocketAddress(port));
            ByteBuffer buffer = ByteBuffer.allocate(65536);

            while (true) {
                buffer.clear();
                InetSocketAddress clientAddress = (InetSocketAddress) channel.receive(buffer);

                if (clientAddress == null) {
                    continue;
                }

                buffer.flip();
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                exmp.chat.ChatMessage chatMessage = (exmp.chat.ChatMessage) objectInputStream.readObject();

                clientAddresses.put(chatMessage.getUserId(), clientAddress);

                for (InetSocketAddress otherClient : clientAddresses.values()) {
                    if (!otherClient.equals(clientAddress)) {
                        buffer.flip();
                        channel.send(buffer, otherClient);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
