package exmp.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

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
        usernameField.setMinWidth(200);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMinWidth(200);

        Button loginButton = new Button("Login");
        loginButton.setMinWidth(200);
        loginButton.setOnAction(e -> {
            try {
                login();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        loginButton.setDefaultButton(true);

        Button registerButton = new Button("Register");
        registerButton.setMinWidth(200);
        registerButton.setOnAction(e -> register());

        this.getChildren().addAll(usernameField, passwordField, loginButton, registerButton);
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setSpacing(10);
        this.setAlignment(Pos.CENTER);
    }

    private void login() throws IOException {
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

            exmp.GUI.MainApp mainApp = new exmp.GUI.MainApp(client);
            Scene scene = new Scene(mainApp, 1245, 850);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Products map");

            exmp.GUI.ChatWindow chatWindow = new exmp.GUI.ChatWindow(client.getUserId());
            chatWindow.show();
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