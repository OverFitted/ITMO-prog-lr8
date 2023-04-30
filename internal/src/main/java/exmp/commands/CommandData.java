package exmp.commands;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class CommandData implements Serializable {
    private final String commandName;
    private final String arguments;
    private String token;
    InetSocketAddress clientAddress;

    private Long userId;

    public CommandData(String commandName, String arguments) {
        this.commandName = commandName;
        this.arguments = arguments;
    }

    public InetSocketAddress getClientAddress() {
        return this.clientAddress;
    }

    public void setClientAddress(InetSocketAddress clientAddress) {
        this.clientAddress = clientAddress;
    }

    public Long getUserId(){
        return this.userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getCommandName() {
        return commandName;
    }

    public String getArguments() {
        return arguments;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
