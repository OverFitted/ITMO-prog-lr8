package exmp.enums;

/**
 * Перечисление цветов.
 */
public enum Color {
    /**
     * Зеленый цвет.
     */
    GREEN,
    /**
     * Синий цвет.
     */
    BLUE,

    /**
     * Оранжевый цвет.
     */
    ORANGE,

    /**
     * Белый цвет.
     */
    WHITE;

    /**
     * Метод, возвращающий все возможные значения перечисления в виде строки.
     * @return - строка со всеми возможными значениями перечисления.
     */
    public static String getValues() {
        StringBuilder sb = new StringBuilder();
        for (exmp.enums.Color unit : exmp.enums.Color.values()) {
            sb.append(unit.toString());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }
}

//public enum Color {
//    RED,
//    BLUE,
//    YELLOW,
//    ORANGE,
//    WHITE;
//}
