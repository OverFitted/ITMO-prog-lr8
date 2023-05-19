package exmp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUIApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        exmp.Client client = new exmp.Client("localhost", 38761);
        exmp.GUI.LoginScreen loginScreen = new exmp.GUI.LoginScreen(client, primaryStage);
        Scene scene = new Scene(loginScreen, 250, 150);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }
}
