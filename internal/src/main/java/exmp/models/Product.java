package exmp.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import exmp.enums.UnitOfMeasure;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс Product, представляющий объект продукта в коллекции.
 */
public class Product implements Comparable<Product> {
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
    private Long id;

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

    private long userId;

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
    @JsonCreator
    public Product(@JsonProperty("name") String name,
                   @JsonProperty("coordinates") Coordinates coordinates,
                   @JsonProperty("price") int price,
                   @JsonProperty("partNumber") String partNumber,
                   @JsonProperty("manufactureCost") Float manufactureCost,
                   @JsonProperty("unitOfMeasure") UnitOfMeasure unitOfMeasure,
                   @JsonProperty("owner") Person owner) {
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

    public Product(Long id,
                   String name,
                   Coordinates coordinates,
                   int price,
                   String partNumber,
                   Float manufactureCost,
                   UnitOfMeasure unitOfMeasure,
                   Person owner,
                   Long userId) {
        this.id = id;
        setName(name);
        setCoordinates(coordinates);
        setCreationDate(new java.util.Date());
        setPrice(price);
        setPartNumber(partNumber);
        setManufactureCost(manufactureCost);
        setUnitOfMeasure(unitOfMeasure);
        setOwner(owner);
        setUserId(userId);
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    /**
     * Метод, возвращающий идентификатор продукта.
     *
     * @return - идентификатор продукта.
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * Возвращает строковое представление объекта Product.
     *
     * @return строковое представление объекта Product, содержащее значения всех полей объекта
     */
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name=" + name +
                ", coordinates=" + coordinates +
                ", creationDate=" + creationDate +
                ", price=" + price +
                ", partNumber=" + partNumber +
                ", unitOfMeasure=" + unitOfMeasure +
                ", owner=" + owner +
                '}';
    }

    /**
     * Сравнивает объекты типа Product по их цене.
     *
     * @param other объект типа Product для сравнения
     * @return отрицательное число, если цена данного объекта меньше чем у other;
     * положительное число, если цена данного объекта больше чем у other;
     * 0, если цены равны
     */
    @Override
    public int compareTo(Product other) {
        return Integer.compare(this.getPrice(), other.getPrice());
    }


    public void setId(long id) {
        if (id < 0) {
            System.err.println("Поле id должно быть больше 0");
        }
        this.id = id;
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
            System.err.println("Поле name не может быть null или пустым");
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
            System.err.println("Поле coordinates не может быть null");
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
            System.err.println("Поле creationDate не может быть null");
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
            System.err.println("Значение поля price должно быть больше 0");
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
            System.err.println("Длина строки partNumber должна быть не меньше 30 и не больше 51");
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
            System.err.println("Поле manufactureCost не может быть null");
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
            System.err.println("Поле unitOfMeasure не может быть null");
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
