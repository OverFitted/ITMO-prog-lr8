package exmp.commands;

import java.io.Serializable;
import java.net.InetSocketAddress;

public class CommandResult implements Serializable {
    private final int statusCode;
    private final String output;
    private final String errorMessage;
    private String token;
    private Long userId;

    public CommandResult(int statusCode, String output, String errorMessage) {
        this.statusCode = statusCode;
        this.output = output;
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "CommandResult{" +
                "statusCode=" + statusCode +
                ", output=" + output +
                ", errorMessage=" + errorMessage +
                "}";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getOutput() {
        return output;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
