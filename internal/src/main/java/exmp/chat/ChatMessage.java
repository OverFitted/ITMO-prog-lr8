package exmp.chat;

import java.io.Serializable;

public class ChatMessage implements Serializable {
    long userId;
    String chatMessage;

    public ChatMessage(long userId, String chatMessage){
        setChatMessage(chatMessage);
        setUserId(userId);
    }

    public String getChatMessage() {
        return chatMessage;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setChatMessage(String chatMessage) {
        this.chatMessage = chatMessage;
    }
}
