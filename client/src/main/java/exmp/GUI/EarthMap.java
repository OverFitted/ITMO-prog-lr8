package exmp.GUI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exmp.commands.CommandResult;
import exmp.enums.Country;
import exmp.models.Product;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EarthMap extends Pane {
    exmp.Client client;
    private Map<Country, StackPane> productIcons;

    public EarthMap(exmp.Client client) {
        this.client = client;
        productIcons = new HashMap<exmp.enums.Country, StackPane>();

        Image earthImage = new Image("world-map.png");
        ImageView earthImageView = new ImageView(earthImage);
        earthImageView.setFitWidth(995);
        earthImageView.setPreserveRatio(true);
        this.getChildren().add(earthImageView);

        for (exmp.enums.Country country : exmp.enums.Country.values()) {
            String[] commandParts = client.getInputParts("filter_by_country " + country.name());
            CommandResult<List<?>> commandResult = client.sendCommand(commandParts);
            if (commandResult.getStatusCode() == 0 && commandResult.getRawOutput().size() > 0) {
                StackPane productIcon = createProductIcon(country, commandResult.getRawOutput());
                productIcons.put(country, productIcon);
                this.getChildren().add(productIcon);
            }
        }
    }

    private void updateProduct(TableColumn.CellEditEvent<Product, ?> e){
        Product product = e.getTableView().getItems().get(e.getTablePosition().getRow());
        String newProduct = product.toString();
        String[] commandParts = client.getInputParts("update " + product.getId() + " " + newProduct);
        CommandResult<List<?>> commandResult = client.sendCommand(commandParts);

        if (commandResult.getStatusCode() != 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Failed");
            alert.setHeaderText(null);
            alert.setContentText("Update failed. Please check your connection.");
            alert.showAndWait();
        }
    }

    private void showCountryProductsInterface(exmp.enums.Country country, List<Product> products) {
        Stage stage = new Stage();
        stage.setTitle("Products in " + country.name());

        TableView<Product> tableView = new TableView<>();
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        nameColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
            this.updateProduct(e);
        });

        TableColumn<Product, exmp.models.Coordinates> coordinatesColumn = new TableColumn<>("Coordinates");
        coordinatesColumn.setCellValueFactory(new PropertyValueFactory<>("coordinates"));
        coordinatesColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            private final ObjectMapper mapper = new ObjectMapper();

            @Override
            public String toString(exmp.models.Coordinates coordinates) {
                return coordinates != null ? coordinates.toString() : "";
            }

            @Override
            public exmp.models.Coordinates fromString(String s) {
                try {
                    return mapper.readValue(s, exmp.models.Coordinates.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }));

        coordinatesColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setCoordinates(e.getNewValue());
            this.updateProduct(e);
        });

        TableColumn<Product, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        priceColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setPrice(e.getNewValue());
            this.updateProduct(e);
        });

        TableColumn<Product, String> partNumberColumn = new TableColumn<>("Part Number");
        partNumberColumn.setCellValueFactory(new PropertyValueFactory<>("partNumber"));
        partNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        partNumberColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
            this.updateProduct(e);
        });

        TableColumn<Product, Float> manufactureCostColumn = new TableColumn<>("Manufacture Cost");
        manufactureCostColumn.setCellValueFactory(new PropertyValueFactory<>("manufactureCost"));
        manufactureCostColumn.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
        manufactureCostColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setManufactureCost(e.getNewValue());
            this.updateProduct(e);
        });

        TableColumn<Product, exmp.enums.UnitOfMeasure> unitOfMeasureColumn = new TableColumn<>("Unit Of Measure");
        unitOfMeasureColumn.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));
        unitOfMeasureColumn.setCellFactory(ComboBoxTableCell.forTableColumn(exmp.enums.UnitOfMeasure.values()));
        unitOfMeasureColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setUnitOfMeasure(e.getNewValue());
            this.updateProduct(e);
        });

        TableColumn<Product, exmp.models.Person> ownerColumn = new TableColumn<>("Owner");
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        ownerColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<>() {
            private final ObjectMapper mapper = new ObjectMapper();

            @Override
            public String toString(exmp.models.Person person) {
                return person != null ? person.toString() : "";
            }

            @Override
            public exmp.models.Person fromString(String s) {
                try {
                    return mapper.readValue(s, exmp.models.Person.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }));
        ownerColumn.setOnEditCommit(e -> {
            e.getTableView().getItems().get(e.getTablePosition().getRow()).setOwner(e.getNewValue());
            this.updateProduct(e);
        });

        tableView.getColumns().add(nameColumn);
        tableView.getColumns().add(coordinatesColumn);
        tableView.getColumns().add(priceColumn);
        tableView.getColumns().add(partNumberColumn);
        tableView.getColumns().add(manufactureCostColumn);
        tableView.getColumns().add(unitOfMeasureColumn);
        tableView.getColumns().add(ownerColumn);

        tableView.getItems().addAll(products);
        tableView.setEditable(true);

        VBox vBox = new VBox(tableView);
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
        stage.show();
    }

    private StackPane createProductIcon(exmp.enums.Country country, List<?> products) {
        double x = 0;
        double y = 0;

        switch (country) {
            case RUSSIA -> {
                x = 760;
                y = 230;
            }
            case INDIA -> {
                x = 685;
                y = 395;
            }
            case THAILAND -> {
                x = 750;
                y = 435;
            }
            case NORTH_KOREA -> {
                x = 815;
                y = 315;
            }
        }

        Circle productIcon = new Circle(10, Color.DARKRED);
        Text productCountText = new Text(String.valueOf(products.size()));
        productCountText.setFill(Color.WHITE);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(productIcon, productCountText);
        stack.setLayoutX(x);
        stack.setLayoutY(y);

        Tooltip tooltip = new Tooltip();
        StringBuilder tooltipText = new StringBuilder();
        for (Product product : (List<Product>) products) {
            tooltipText.append("Name: ").append(product.getName()).append("\n");
            tooltipText.append("Price: ").append(product.getPrice()).append("\n");
            tooltipText.append("Part Number: ").append(product.getPartNumber()).append("\n");
            tooltipText.append("Manufacture Cost: ").append(product.getManufactureCost()).append("\n");
            tooltipText.append("Unit of Measure: ").append(product.getUnitOfMeasure()).append("\n");
            tooltipText.append("Owner: ").append(product.getOwner().getName()).append("\n\n");
        }
        tooltip.setText(tooltipText.toString());
        tooltip.setPrefWidth(200);
        tooltip.setWrapText(true);

        Tooltip.install(stack, tooltip);
        stack.setOnMouseClicked(event -> showCountryProductsInterface(country, (List<Product>) products));

        return stack;
    }
}
