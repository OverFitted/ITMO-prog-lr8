package exmp.models;

import exmp.enums.Color;
import exmp.enums.Country;
import exmp.models.Location;

public class Person {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Long height; //Поле может быть null, Значение поля должно быть больше 0
    private Color eyeColor; //Поле не может быть null
    private Color hairColor; //Поле может быть null
    private Country nationality; //Поле может быть null
    private Location location; //Поле не может быть null

    public Person(String name, Long height, Color eyeColor, Color hairColor, Country nationality, Location location) {
        setName(name);
        setHeight(height);
        setEyeColor(eyeColor);
        setHairColor(hairColor);
        setNationality(nationality);
        setLocation(location);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Поле name не может быть null или пустым");
        }
        this.name = name;
    }

    public Long getHeight() {
        return this.height;
    }

    public void setHeight(Long height) {
        if (height != null && height <= 0) {
            throw new IllegalArgumentException("Значение поля height должно быть больше 0");
        }
        this.height = height;
    }

    public Color getEyeColor() {
        return this.eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        if (eyeColor == null) {
            throw new IllegalArgumentException("Поле eyeColor не может быть null");
        }
        this.eyeColor = eyeColor;
    }

    public Color getHairColor() {
        return this.hairColor;
    }

    public void setHairColor(Color hairColor) {
        this.hairColor = hairColor;
    }

    public Country getNationality() {
        return this.nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        if (location == null) {
            throw new IllegalArgumentException("Поле location не может быть null");
        }
        this.location = location;
    }
}
