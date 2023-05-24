package exmp.GUI;

import exmp.ChatClient;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ChatWindow extends Stage {
    private final TextArea chatArea;
    private final TextField messageField;

    private final long userId;
    private final exmp.ChatClient chatClient;

    public ChatWindow(long userId) throws IOException {
        this.userId = userId;
        int chatPort = 13231;
        this.chatClient = new ChatClient(userId, "localhost", chatPort, this);

        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setPrefHeight(400);

        messageField = new TextField();
        messageField.setPrefHeight(50);
        messageField.setOnAction(e -> {
            try {
                sendMessage();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        layout.getChildren().addAll(chatArea, messageField);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 500);
        this.setScene(scene);
        this.setTitle("Chat");

        new Thread(() -> {
            try {
                chatClient.startReceiving();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }

    private void sendMessage() throws IOException {
        String message = messageField.getText();

        if (!message.isEmpty()) {
            chatArea.appendText(userId + ": " + message + "\n");
            chatClient.sendMessage(message);
            messageField.clear();
        }
    }

    public void receiveMessage(String message, long userId) {
        Platform.runLater(() -> chatArea.appendText(userId + ": " + message + "\n"));
    }
}
