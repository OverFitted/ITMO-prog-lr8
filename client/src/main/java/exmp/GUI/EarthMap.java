package exmp.GUI;

import exmp.commands.CommandResult;
import exmp.enums.Country;
import exmp.models.Product;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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

        return stack;
    }
}
