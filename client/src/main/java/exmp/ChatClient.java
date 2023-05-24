package exmp;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ChatClient {
    private final long userId;
    private final InetSocketAddress serverAddress;
    private final DatagramChannel channel;
    private final exmp.GUI.ChatWindow chatWindow;

    public ChatClient(long userId, String serverIp, int serverPort, exmp.GUI.ChatWindow chatWindow) throws IOException {
        this.userId = userId;
        this.chatWindow = chatWindow;
        this.serverAddress = new InetSocketAddress(serverIp, serverPort);
        this.channel = DatagramChannel.open();
    }

    public void sendMessage(String message) throws IOException {
        exmp.chat.ChatMessage chatMessage = new exmp.chat.ChatMessage(userId, message);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(chatMessage);
        byte[] data = byteArrayOutputStream.toByteArray();

        channel.send(ByteBuffer.wrap(data), serverAddress);
    }

    public void startReceiving() throws IOException, ClassNotFoundException {
        ByteBuffer buffer = ByteBuffer.allocate(65536);
        while (true) {
            buffer.clear();
            channel.receive(buffer);

            buffer.flip();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer.array(), 0, buffer.limit());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            exmp.chat.ChatMessage chatMessage = (exmp.chat.ChatMessage) objectInputStream.readObject();

            chatWindow.receiveMessage(chatMessage.getChatMessage(), chatMessage.getUserId());
        }
    }
}
