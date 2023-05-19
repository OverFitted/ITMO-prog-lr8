package exmp.GUI;

import exmp.commands.CommandDescriptor;
import exmp.commands.CommandResult;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainApp extends BorderPane {
    exmp.Client client;
    private exmp.GUI.EarthMap earthMap;
    private ListView<String> commandList;
    List<exmp.commands.CommandDescriptor> commands;
    private TextArea outputArea;

    public MainApp(exmp.Client client) {
        this.client = client;

        earthMap = new exmp.GUI.EarthMap(client);
        commandList = new ListView<>();
        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);

        commandList.setPrefWidth(250);
        outputArea.setPrefHeight(130);

        String[] commandParts = client.getInputParts("help");
        CommandResult<List<?>> commandResult = client.sendCommand(commandParts);
        commands = (List<exmp.commands.CommandDescriptor>) commandResult.getRawOutput();
        commandList.getItems().addAll(commands.stream().map(CommandDescriptor::getName).toList());

        commandList.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                executeCommand(newSelection);
            }
        });

        this.setCenter(earthMap);
        this.setRight(commandList);
        this.setBottom(outputArea);
    }

    private void executeCommand(String command) {
        // Get the command descriptor for the selected command
        CommandDescriptor commandDescriptor = commands.stream()
                .filter(c -> c.getName().equals(command))
                .findFirst()
                .orElse(null);

        if (commandDescriptor == null) {
            return;
        }

        Stage commandArgsStage = new Stage();
        commandArgsStage.setTitle("Enter command arguments");

        VBox form = new VBox();
        form.setSpacing(10);
        form.setPadding(new Insets(10, 10, 10, 10)); // set padding
        form.setAlignment(Pos.CENTER); // align elements to the center

        List<TextField> argFields = new ArrayList<>();

        for (exmp.commands.ArgDescriptor argDescriptor : commandDescriptor.getArgDescriptors()) {
            TextField argField = new TextField();
            argField.setPromptText(argDescriptor.description());
            form.getChildren().add(argField);
            argFields.add(argField);
        }

        Button executeButton = new Button("Execute");
        executeButton.setMinWidth(100);
        executeButton.setOnAction(e -> {
            List<String> args = argFields.stream()
                    .map(TextField::getText)
                    .collect(Collectors.toList());

            String input = commandDescriptor.getCommand() + " " + String.join(" ", args);
            String[] commandParts = client.getInputParts(input);
            CommandResult<List<?>> commandResult = client.sendCommand(commandParts);
            outputArea.setText(commandResult.getOutput());

            commandArgsStage.close();
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> commandArgsStage.close());
        cancelButton.setMinWidth(100);

        form.getChildren().addAll(executeButton, cancelButton);

        Scene scene = new Scene(form);
        commandArgsStage.setScene(scene);
        commandArgsStage.show();
    }
}
