package exmp.models;

public class Coordinates {
    private Double x;
    private Float y;

    public Coordinates(Double x, Float y) {
        setX(x);
        setY(y);
    }

    public Double getX() {
        return this.x;
    }

    public void setX(Double x) {
        if (x == null) {
            throw new IllegalArgumentException("Поле x не может быть null");
        }
        this.x = x;
    }

    public Float getY() {
        return this.y;
    }

    public void setY(Float y) {
        if (y == null || y <= -452) {
            throw new IllegalArgumentException("Значение поля y должно быть больше -452 и не может быть null");
        }
        this.y = y;
    }
}
