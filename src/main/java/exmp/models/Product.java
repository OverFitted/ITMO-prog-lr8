package exmp.models;

import exmp.enums.UnitOfMeasure;

public class Product {
    private Long id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого
                     // поля должно быть уникальным, Значение этого поля должно генерироваться
                     // автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private java.util.Date creationDate; // Поле не может быть null, Значение этого поля должно генерироваться
                                         // автоматически
    private int price; // Значение поля должно быть больше 0
    private String partNumber; // Длина строки должна быть не меньше 30, Длина строки не должна быть больше 51,
                               // Строка не может быть пустой, Поле может быть null
    private Float manufactureCost; // Поле не может быть null
    private UnitOfMeasure unitOfMeasure; // Поле не может быть null
    private Person owner; // Поле может быть null
}
