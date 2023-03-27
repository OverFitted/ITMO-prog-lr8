package exmp.models;

import exmp.enums.UnitOfMeasure;
import exmp.models.Coordinates;
import exmp.models.Person;

import java.util.concurrent.atomic.AtomicLong;

public class Product {
    private static AtomicLong idGenerator = new AtomicLong(1);
    private Long id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private java.util.Date creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private int price; // Значение поля должно быть больше 0
    private String partNumber; // Длина строки должна быть не меньше 30, Длина строки не должна быть больше 51, cтрока не может быть пустой, Поле может быть null
    private Float manufactureCost; // Поле не может быть null
    private UnitOfMeasure unitOfMeasure; // Поле не может быть null
    private Person owner; // Поле может быть null

    public Product(String name, Coordinates coordinates, int price, String partNumber, Float manufactureCost, UnitOfMeasure unitOfMeasure, Person owner) {
        this.id = idGenerator.getAndIncrement();
        setName(name);
        setCoordinates(coordinates);
        setCreationDate(new java.util.Date());
        setPrice(price);
        setPartNumber(partNumber);
        setManufactureCost(manufactureCost);
        setUnitOfMeasure(unitOfMeasure);
        setOwner(owner);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Поле name не может быть null или пустым");
        }
        this.name = name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Поле coordinates не может быть null");
        }
        this.coordinates = coordinates;
    }

    public java.util.Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.util.Date creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("Поле creationDate не может быть null");
        }
        this.creationDate = creationDate;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Значение поля price должно быть больше 0");
        }
        this.price = price;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        if (partNumber != null && (partNumber.length() < 30 || partNumber.length() > 51)) {
            throw new IllegalArgumentException("Длина строки partNumber должна быть не меньше 30 и не больше 51");
        }
        this.partNumber = partNumber;
    }

    public Float getManufactureCost() {
        return manufactureCost;
    }

    public void setManufactureCost(Float manufactureCost) {
        if (manufactureCost == null) {
            throw new IllegalArgumentException("Поле manufactureCost не может быть null");
        }
        this.manufactureCost = manufactureCost;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == null) {
            throw new IllegalArgumentException("Поле unitOfMeasure не может быть null");
        }
        this.unitOfMeasure = unitOfMeasure;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }
}
