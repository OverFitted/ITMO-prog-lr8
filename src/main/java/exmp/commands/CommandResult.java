package exmp.commands;

import java.io.Serializable;

public class CommandResult implements Serializable {
    private int statusCode;
    private String output;
    private String errorMessage;

    public CommandResult(int statusCode, String output, String errorMessage) {
        this.statusCode = statusCode;
        this.output = output;
        this.errorMessage = errorMessage;
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
