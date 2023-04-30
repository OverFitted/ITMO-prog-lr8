package exmp.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Класс, представляющий местоположение.
 */
public class Location {
    private long id;
    private float x;
    private double y;
    private double z;
    private String name; // Поле может быть null

    /**
     * Конструктор класса Location.
     *
     * @param x    координата x местоположения
     * @param y    координата y местоположения
     * @param z    координата z местоположения
     * @param name название местоположения
     */
    @JsonCreator
    public Location(@JsonProperty("x") float x,
                    @JsonProperty("y") double y,
                    @JsonProperty("z") double z,
                    @JsonProperty("name") String name) {
        setX(x);
        setY(y);
        setZ(z);
        setName(name);
    }

    public Location(Long id, float x, double y, double z, String name) {
        setId(id);
        setX(x);
        setY(y);
        setZ(z);
        setName(name);
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return this.id;
    }

    /**
     * Возвращает строковое представление объекта Location.
     *
     * @return строковое представление объекта Location, содержащее значения всех полей объекта
     */
    @Override
    public String toString() {
        return "Location{" +
                "X=" + this.getX() +
                ", Y=" + this.getY() +
                ", Z=" + this.getZ() +
                ", Name=" + this.getName() +
                '}';
    }

    /**
     * Получает координату x местоположения.
     *
     * @return координата x местоположения
     */
    public float getX() {
        return this.x;
    }

    /**
     * Устанавливает координату x местоположения.
     *
     * @param x координата x местоположения
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Получает координату y местоположения.
     *
     * @return координата y местоположения
     */
    public double getY() {
        return this.y;
    }

    /**
     * Устанавливает координату y местоположения.
     *
     * @param y координата y местоположения
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Получает координату z местоположения.
     *
     * @return координата z местоположения
     */
    public double getZ() {
        return this.z;
    }

    /**
     * Устанавливает координату z местоположения.
     *
     * @param z координата z местоположения
     */
    public void setZ(double z) {
        this.z = z;
    }

    /**
     * Получает название местоположения.
     *
     * @return название местоположения
     */
    public String getName() {
        return this.name;
    }

    /**
     * Устанавливает название местоположения.
     *
     * @param name название местоположения
     */
    public void setName(String name) {
        this.name = name;
    }
}
