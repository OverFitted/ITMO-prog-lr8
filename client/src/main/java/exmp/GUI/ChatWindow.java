package exmp.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChatWindow extends Stage {
    private TextArea chatArea;
    private TextField messageField;

    public ChatWindow() {
        VBox layout = new VBox();
        layout.setSpacing(10);
        layout.setPadding(new Insets(10, 10, 10, 10));

        chatArea = new TextArea();
        chatArea.setEditable(false);
        chatArea.setPrefHeight(400);

        messageField = new TextField();
        messageField.setPrefHeight(50);
        messageField.setOnAction(e -> sendMessage());

        layout.getChildren().addAll(chatArea, messageField);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout, 300, 500);
        this.setScene(scene);
        this.setTitle("Chat");
    }

    private void sendMessage() {
        String message = messageField.getText();
        if (!message.isEmpty()) {
            chatArea.appendText(message + "\n");
            messageField.clear();

            // Implement chat message sending logic here
        }
    }
}