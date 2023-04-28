package exmp.commands;

import java.io.Serializable;

public class CommandResult implements Serializable {
    private final int statusCode;
    private final String output;
    private final String errorMessage;

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

    public int getStatusCode() {
        return statusCode;
    }

    public String getOutput() {
        return output;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isSuccess() {
        return statusCode == 0;
    }
}
