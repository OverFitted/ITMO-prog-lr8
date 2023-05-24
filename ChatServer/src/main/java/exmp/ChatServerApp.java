package exmp;

public class ChatServerApp {
    static int chatPort = 13232;

    public static void main(String[] args) {
        exmp.ChatServer chatServer = new exmp.ChatServer(chatPort);
        chatServer.start();
    }
}
