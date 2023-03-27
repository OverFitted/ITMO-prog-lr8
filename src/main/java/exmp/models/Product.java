package exmp.models;

import exmp.enums.UnitOfMeasure;
import exmp.models.Person;
import exmp.models.Coordinates;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс Product, представляющий объект продукта в коллекции.
 */
public class Product {
    /**
     * Генератор идентификаторов для объектов класса Product.
     */
    private static final AtomicLong idGenerator = new AtomicLong(1);

    /**
     * Уникальный идентификатор объекта.
     * Поле не может быть null.
     * Значение поля должно быть больше 0.
     * Значение этого поля должно быть уникальным.
     * Значение этого поля должно генерироваться автоматически.
     */
    private final Long id;

    /**
     * Название продукта.
     * Поле не может быть null.
     * Строка не может быть пустой.
     */
    private String name;

    /**
     * Координаты продукта.
     * Поле не может быть null.
     */
    private Coordinates coordinates;

    /**
     * Дата создания объекта.
     * Поле не может быть null.
     * Значение этого поля должно генерироваться автоматически.
     */
    private java.util.Date creationDate;

    /**
     * Цена продукта.
     * Значение поля должно быть больше 0.
     */
    private int price;

    /**
     * Номер части продукта.
     * Длина строки должна быть не меньше 30.
     * Длина строки не должна быть больше 51.
     * Строка не может быть пустой.
     * Поле может быть null.
     */
    private String partNumber;

    /**
     * Стоимость производства продукта.
     * Поле не может быть null.
     */
    private Float manufactureCost;

    /**
     * Единица измерения продукта.
     * Поле не может быть null.
     */
    private UnitOfMeasure unitOfMeasure;

    /**
     * Владелец продукта.
     * Поле может быть null.
     */
    private Person owner;

    /**
     * Конструктор класса Product.
     *
     * @param name            - название продукта.
     * @param coordinates     - координаты продукта.
     * @param price           - цена продукта.
     * @param partNumber      - номер части продукта.
     * @param manufactureCost - стоимость производства продукта.
     * @param unitOfMeasure   - единица измерения продукта.
     * @param owner           - владелец продукта.
     */
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

    /**
     * Метод, возвращающий идентификатор продукта.
     *
     * @return - идентификатор продукта.
     */
    public Long getId() {
        return id;
    }

    /**
     * Метод, возвращающий название продукта.
     *
     * @return - название продукта.
     */
    public String getName() {
        return name;
    }

    /**
     * Метод, устанавливающий название продукта.
     *
     * @param name - название продукта.
     */
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Поле name не может быть null или пустым");
        }
        this.name = name;
    }

    /**
     * Метод, возвращающий координаты продукта.
     *
     * @return - координаты продукта.
     */
    public Coordinates getCoordinates() {
        return coordinates;
    }

    /**
     * Метод, устанавливающий координаты продукта.
     *
     * @param coordinates - координаты продукта.
     */
    public void setCoordinates(Coordinates coordinates) {
        if (coordinates == null) {
            throw new IllegalArgumentException("Поле coordinates не может быть null");
        }
        this.coordinates = coordinates;
    }

    /**
     * Метод, возвращающий дату создания продукта.
     *
     * @return - дата создания продукта.
     */
    public java.util.Date getCreationDate() {
        return creationDate;
    }

    /**
     * Метод, устанавливающий дату создания продукта.
     *
     * @param creationDate - дата создания продукта.
     */
    public void setCreationDate(java.util.Date creationDate) {
        if (creationDate == null) {
            throw new IllegalArgumentException("Поле creationDate не может быть null");
        }
        this.creationDate = creationDate;
    }

    /**
     * Метод, возвращающий цену продукта.
     *
     * @return - цена продукта.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Метод, устанавливающий цену продукта.
     *
     * @param price - цена продукта.
     */
    public void setPrice(int price) {
        if (price <= 0) {
            throw new IllegalArgumentException("Значение поля price должно быть больше 0");
        }
        this.price = price;
    }

    /**
     * Метод, возвращающий номер части продукта.
     *
     * @return - номер части продукта.
     */
    public String getPartNumber() {
        return partNumber;
    }

    /**
     * Метод, устанавливающий номер части продукта.
     *
     * @param partNumber - номер части продукта.
     */
    public void setPartNumber(String partNumber) {
        if (partNumber != null && (partNumber.length() < 30 || partNumber.length() > 51)) {
            throw new IllegalArgumentException("Длина строки partNumber должна быть не меньше 30 и не больше 51");
        }
        this.partNumber = partNumber;
    }

    /**
     * Метод, возвращающий стоимость производства продукта.
     *
     * @return - стоимость производства продукта.
     */
    public Float getManufactureCost() {
        return manufactureCost;
    }

    /**
     * Метод, устанавливающий стоимость производства продукта.
     *
     * @param manufactureCost - стоимость производства продукта.
     */
    public void setManufactureCost(Float manufactureCost) {
        if (manufactureCost == null) {
            throw new IllegalArgumentException("Поле manufactureCost не может быть null");
        }
        this.manufactureCost = manufactureCost;
    }

    /**
     * Метод, возвращающий единицу измерения продукта.
     *
     * @return - единица измерения продукта.
     */
    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    /**
     * Метод, устанавливающий единицу измерения продукта.
     *
     * @param unitOfMeasure - единица измерения продукта.
     */
    public void setUnitOfMeasure(UnitOfMeasure unitOfMeasure) {
        if (unitOfMeasure == null) {
            throw new IllegalArgumentException("Поле unitOfMeasure не может быть null");
        }
        this.unitOfMeasure = unitOfMeasure;
    }

    /**
     * Метод, возвращающий владельца продукта.
     *
     * @return - владелец продукта.
     */
    public Person getOwner() {
        return owner;
    }

    /**
     * Метод, устанавливающий владельца продукта.
     *
     * @param owner - владелец продукта.
     */
    public void setOwner(Person owner) {
        this.owner = owner;
    }

}
