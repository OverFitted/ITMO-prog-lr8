package exmp.GUI;

import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListView;

public class MainApp extends BorderPane {

    private exmp.GUI.EarthMap earthMap;
    private ListView<String> commandList;

    public MainApp() {
        earthMap = new exmp.GUI.EarthMap();
        commandList = new ListView<>();

        commandList.getItems().addAll("Add product", "Remove product");
        commandList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                executeCommand(newSelection);
            }
        });

        this.setCenter(earthMap);
        this.setRight(commandList);
    }

    private void executeCommand(String command) {
        // Execution logic here
    }
}

