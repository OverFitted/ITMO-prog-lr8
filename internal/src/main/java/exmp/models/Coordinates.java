package exmp.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Класс, представляющий координаты объекта
 */
public class Coordinates {
    private long id;
    private Double x; // Координата x. Поле не может быть null
    private Float y; // Координата y. Значение поля должно быть больше -452 и не может быть null

    /**
     * Конструктор класса Coordinates
     *
     * @param x координата x
     * @param y координата y
     * @throws IllegalArgumentException если x == null или y <= -452
     */
    @JsonCreator
    public Coordinates(@JsonProperty("x") Double x, @JsonProperty("y") Float y) {
        setX(x);
        setY(y);
    }

    public Coordinates(Long id, Double x, Float y) {
        setId(id);
        setX(x);
        setY(y);
    }

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return this.id;
    }

    /**
     * Возвращает строковое представление объекта Coordinates.
     *
     * @return строковое представление объекта Coordinates, содержащее значения всех полей объекта
     */
    @Override
    public String toString() {
        return "Coordinates{" +
                "X=" + this.getX() +
                ", Y=" + this.getY() +
                '}';
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
            System.err.println("Поле x не может быть null");
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
            System.err.println("Значение поля y должно быть больше -452 и не может быть null");
        }
        this.y = y;
    }
}
