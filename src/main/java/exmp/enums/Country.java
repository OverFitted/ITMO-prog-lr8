package exmp.enums;

/**
 * Перечисление стран.
 */
public enum Country {
    /**
     * Россия.
     */
    RUSSIA,
    /**
     * Индия.
     */
    INDIA,
    /**
     * Таиланд.
     */
    THAILAND,
    /**
     * Северная Корея.
     */
    NORTH_KOREA;

    /**
     * Метод, возвращающий все возможные значения перечисления в виде строки.
     * @return - строка со всеми возможными значениями перечисления.
     */
    public static String getValues() {
        StringBuilder sb = new StringBuilder();
        for (exmp.enums.Country unit : exmp.enums.Country.values()) {
            sb.append(unit.toString());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }
}
