package exmp.GUI;

import exmp.commands.CommandDescriptor;
import exmp.commands.CommandResult;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

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
        commandList.setCellFactory(new Callback<>() {
            @Override
            public ListCell<String> call(ListView<String> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            setText(item);
                            setStyle("-fx-font-weight: bold; -fx-padding: 5;");
                        }
                    }
                };
            }
        });

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
        form.setPadding(new Insets(10, 10, 10, 10));
        form.setAlignment(Pos.CENTER);
        form.setStyle("-fx-background-color: #EEE; -fx-border-color: #666; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px;");
        form.setPrefWidth(320);

        List<TextField> argFields = new ArrayList<>();

        for (exmp.commands.ArgDescriptor argDescriptor : commandDescriptor.getArgDescriptors()) {
            TextField argField = new TextField();
            argField.setPromptText(argDescriptor.description());
            argField.setStyle("-fx-padding: 3; -fx-border-color: #AAA; -fx-border-radius: 3px;");
            form.getChildren().add(argField);
            argFields.add(argField);
        }

        Button executeButton = new Button("Execute");
        executeButton.setMinWidth(100);
        executeButton.setStyle("-fx-background-color: #228B22; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-background-radius: 5px;");
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
        cancelButton.setStyle("-fx-background-color: #DC143C; -fx-text-fill: white; -fx-font-weight: bold; -fx-border-radius: 5px; -fx-background-radius: 5px;");

        form.getChildren().addAll(executeButton, cancelButton);

        Scene scene = new Scene(form);
        commandArgsStage.setScene(scene);
        commandArgsStage.show();
    }
}
