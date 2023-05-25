package exmp.GUI;

import exmp.models.Product;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.List;

public class ProductTableView extends TableView<Product> {
    public ProductTableView(List<Product> products) {
        TableColumn<Product, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, exmp.models.Coordinates> coordinatesColumn = new TableColumn<>("Coordinates");
        coordinatesColumn.setCellValueFactory(new PropertyValueFactory<>("coordinates"));

        TableColumn<Product, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Product, String> partNumberColumn = new TableColumn<>("Part Number");
        partNumberColumn.setCellValueFactory(new PropertyValueFactory<>("partNumber"));

        TableColumn<Product, Float> manufactureCostColumn = new TableColumn<>("Manufacture Cost");
        manufactureCostColumn.setCellValueFactory(new PropertyValueFactory<>("manufactureCost"));

        TableColumn<Product, exmp.enums.UnitOfMeasure> unitOfMeasureColumn = new TableColumn<>("Unit Of Measure");
        unitOfMeasureColumn.setCellValueFactory(new PropertyValueFactory<>("unitOfMeasure"));

        TableColumn<Product, exmp.models.Person> ownerColumn = new TableColumn<>("Owner");
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));

        this.getColumns().add(nameColumn);
        this.getColumns().add(coordinatesColumn);
        this.getColumns().add(priceColumn);
        this.getColumns().add(partNumberColumn);
        this.getColumns().add(manufactureCostColumn);
        this.getColumns().add(unitOfMeasureColumn);
        this.getColumns().add(ownerColumn);

        this.getItems().addAll(products);
    }
}
