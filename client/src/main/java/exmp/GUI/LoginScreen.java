package exmp.GUI;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class LoginScreen extends VBox {
    private final exmp.Client client;
    private final TextField usernameField;
    private final PasswordField passwordField;
    private final Stage stage;

    public LoginScreen(exmp.Client client, Stage stage) {
        this.client = client;
        this.stage = stage;

        usernameField = new TextField();
        usernameField.setPromptText("Username");

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginButton = new Button("Login");
        loginButton.setOnAction(e -> login());

        Button registerButton = new Button("Register");
        registerButton.setOnAction(e -> register());

        this.getChildren().addAll(usernameField, passwordField, loginButton, registerButton);
    }

    private void login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String[] commandParts = client.getInputParts(String.format("login %s %s", username, password));
        exmp.commands.CommandResult commandResult = client.sendCommand(commandParts);

        if (commandResult.getStatusCode() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful");
            alert.setHeaderText(null);
            alert.setContentText("You have successfully logged in.");
            alert.showAndWait();

            exmp.GUI.MainApp mainApp = new exmp.GUI.MainApp();
            Scene scene = new Scene(mainApp, 800, 600);
            stage.setScene(scene);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Login failed. Please check your username and password.");
            alert.showAndWait();
        }
    }

    private void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String[] commandParts = client.getInputParts(String.format("register %s %s", username, password));
        exmp.commands.CommandResult commandResult = client.sendCommand(commandParts);

        if (commandResult.getStatusCode() == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Registration Successful");
            alert.setHeaderText(null);
            alert.setContentText("You have successfully registered. You can now log in.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Registration Failed");
            alert.setHeaderText(null);
            alert.setContentText("Registration failed. Please try again.");
            alert.showAndWait();
        }
    }
}