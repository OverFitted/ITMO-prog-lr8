package exmp.models;

/**
 * Класс, представляющий координаты объекта
 */
public class Coordinates {
    private Double x; // Координата x. Поле не может быть null
    private Float y; // Координата y. Значение поля должно быть больше -452 и не может быть null

    /**
     * Конструктор класса Coordinates
     *
     * @param x координата x
     * @param y координата y
     * @throws IllegalArgumentException если x == null или y <= -452
     */
    public Coordinates(Double x, Float y) {
        setX(x);
        setY(y);
    }

    /**
     * Метод для получения координаты x
     *
     * @return координата x
     */
    public Double getX() {
        return this.x;
    }

    /**
     * Метод для задания координаты x
     *
     * @param x координата x
     * @throws IllegalArgumentException если x == null
     */
    public void setX(Double x) {
        if (x == null) {
            throw new IllegalArgumentException("Поле x не может быть null");
        }
        this.x = x;
    }

    /**
     * Метод для получения координаты y
     *
     * @return координата y
     */
    public Float getY() {
        return this.y;
    }

    /**
     * Метод для задания координаты y
     *
     * @param y координата y
     * @throws IllegalArgumentException если y == null или y <= -452
     */
    public void setY(Float y) {
        if (y == null || y <= -452) {
            throw new IllegalArgumentException("Значение поля y должно быть больше -452 и не может быть null");
        }
        this.y = y;
    }
}
