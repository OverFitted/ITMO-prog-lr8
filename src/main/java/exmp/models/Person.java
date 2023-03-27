package exmp.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import exmp.enums.Color;
import exmp.enums.Country;
import exmp.models.Location;

/**
 * Класс, представляющий сущность "Человек".
 */
public class Person {
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Long height; // Поле может быть null, Значение поля должно быть больше 0
    private Color eyeColor; // Поле не может быть null
    private Color hairColor; // Поле может быть null
    private Country nationality; // Поле может быть null
    private Location location; // Поле не может быть null

    /**
     * Конструктор класса Person.
     * @param name - имя человека.
     * @param height - рост человека.
     * @param eyeColor - цвет глаз человека.
     * @param hairColor - цвет волос человека.
     * @param nationality - национальность человека.
     * @param location - местонахождение человека.
     */
    @JsonCreator
    public Person(@JsonProperty("name") String name,
                  @JsonProperty("height") Long height,
                  @JsonProperty("eyeColor") Color eyeColor,
                  @JsonProperty("hairColor") Color hairColor,
                  @JsonProperty("nationality") Country nationality,
                  @JsonProperty("location") Location location) {
        setName(name);
        setHeight(height);
        setEyeColor(eyeColor);
        setHairColor(hairColor);
        setNationality(nationality);
        setLocation(location);
    }

    @Override
    public String toString() {
        return "Person{" +
                "name=" + this.getName() +
                ", Height=" + this.getHeight() +
                ", EyeColor=" + this.getEyeColor() +
                ", HairColor=" + this.getHairColor() +
                ", Nationality=" + this.getNationality() +
                ", Location=" + this.getLocation() +
                '}';
    }

    /**
     * Метод, возвращающий имя человека.
     * @return - имя человека.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Метод, устанавливающий имя человека.
     * @param name - имя человека.
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Поле name не может быть null или пустым");
        }
        this.name = name;
    }

    /**
     * Метод, возвращающий рост человека.
     * @return - рост человека.
     */
    public Long getHeight() {
        return this.height;
    }

    /**
     * Метод, устанавливающий рост человека.
     * @param height - рост человека.
     */
    public void setHeight(Long height) {
        if (height != null && height <= 0) {
            throw new IllegalArgumentException("Значение поля height должно быть больше 0");
        }
        this.height = height;
    }

    /**
     * Метод, возвращающий цвет глаз человека.
     * @return - цвет глаз человека.
     */
    public Color getEyeColor() {
        return this.eyeColor;
    }

    /**
     * Метод, устанавливающий цвет глаз человека.
     * @param eyeColor - цвет глаз человека.
     */
    public void setEyeColor(Color eyeColor) {
        if (eyeColor == null) {
            throw new IllegalArgumentException("Поле eyeColor не может быть null");
        }
        this.eyeColor = eyeColor;
    }

    /**
     * Метод, возвращающий цвет волос человека.
     * @return - цвет волос человека.
     */
    public Color getHairColor() {
        return this.hairColor;
    }

    /**
     * Метод, устанавливающий цвет волос человека.
     * @param hairColor - цвет глаз человека.
     */
    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    /**
     * Метод, возвращающий национальность человека.
     * @return - национальность человека.
     */
    public Country getNationality() {
        return this.nationality;
    }

    /**
     * Метод, устанавливающий национальность человека.
     * @param nationality - национальность человека.
     */
    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    /**
     * Метод, возвращающий локацию человека.
     * @return - локация человека.
     */
    public Location getLocation() {
        return this.location;
    }

    /**
     * Метод, устанавливающий локацию человека.
     * @param location - локация человека.
     */
    public void setLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Поле location не может быть null");
        }
        this.location = location;
    }
}
