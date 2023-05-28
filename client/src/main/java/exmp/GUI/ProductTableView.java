package exmp.GUI;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import exmp.models.Product;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.List;

public class ProductTableView extends TableView<Product> {
    exmp.Client client;

    public ProductTableView(List<Product> products, exmp.Client client) {
        this.client = client;

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

        this.getColumns().add(nameColumn);
        this.getColumns().add(coordinatesColumn);
        this.getColumns().add(priceColumn);
        this.getColumns().add(partNumberColumn);
        this.getColumns().add(manufactureCostColumn);
        this.getColumns().add(unitOfMeasureColumn);
        this.getColumns().add(ownerColumn);

        this.getItems().addAll(products);
    }

    private void updateProduct(TableColumn.CellEditEvent<Product, ?> e){
        Product product = e.getTableView().getItems().get(e.getTablePosition().getRow());
        String newProduct = product.toString();
        String[] commandParts = client.getInputParts("update " + product.getId() + " " + newProduct);
        exmp.commands.CommandResult<List<?>> commandResult = client.sendCommand(commandParts);

        if (commandResult.getStatusCode() != 0){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Update Failed");
            alert.setHeaderText(null);
            alert.setContentText("Update failed. Please check your connection.");
            alert.showAndWait();
        }
    }
}
