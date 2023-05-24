package exmp;

import java.util.ArrayList;
import java.util.List;

public class ChatServer {
    private List<ClientHandler> clients;

    public ChatServer() {
        clients = new ArrayList<>();
    }

    public void start() {
        // Start the server...
    }

    public void broadcastMessage(exmp.chat.ChatMessage message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }

    private class ClientHandler {
        // ...

        public void sendMessage(exmp.chat.ChatMessage message) {
            // Send the message to the client...
        }
    }
}
