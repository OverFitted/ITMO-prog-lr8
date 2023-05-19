package exmp.GUI;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class EarthMap extends Pane {

    private Map<exmp.enums.Country, Circle> productIcons;

    public EarthMap() {
        productIcons = new HashMap<>();

        // Load the Earth image
        Image earthImage = new Image("file:world-map.png");
        ImageView earthImageView = new ImageView(earthImage);
        this.getChildren().add(earthImageView);

        // Add product icons for each country
        for (exmp.enums.Country country : exmp.enums.Country.values()) {
            Circle productIcon = createProductIcon(country);
            productIcons.put(country, productIcon);
            this.getChildren().add(productIcon);
        }
    }

    private Circle createProductIcon(exmp.enums.Country country) {
        // Replace these coordinates later
        double x = 0;
        double y = 0;

        switch (country) {
            case RUSSIA:
                x = 100;
                y = 100;
                break;
            case INDIA:
                x = 200;
                y = 200;
                break;
            case THAILAND:
                x = 300;
                y = 300;
                break;
            case NORTH_KOREA:
                x = 400;
                y = 400;
                break;
        }

        Circle productIcon = new Circle(x, y, 10, Color.RED);
        productIcon.setOnMouseClicked(e -> {
            // Interesting product logic here. Ill think it over later
        });

        return productIcon;
    }
}
