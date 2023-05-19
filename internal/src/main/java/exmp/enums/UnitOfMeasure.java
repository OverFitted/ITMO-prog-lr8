package exmp.enums;

import java.io.Serializable;

/**
 * Перечисление единиц измерения
 */
public enum UnitOfMeasure implements Serializable {
    /**
     * Килограммы.
     */
    KILOGRAMS,
    /**
     * Метры.
     */
    METERS,
    /**
     * Сантиметры.
     */
    CENTIMETERS,
    /**
     * Квадратные метры.
     */
    SQUARE_METERS,
    /**
     * Миллиграммы.
     */
    MILLIGRAMS;

    /**
     * Метод, возвращающий все возможные значения перечисления в виде строки.
     * @return - строка со всеми возможными значениями перечисления.
     */
    public static String getValues() {
        StringBuilder sb = new StringBuilder();
        for (UnitOfMeasure unit : UnitOfMeasure.values()) {
            sb.append(unit.toString());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        return sb.toString();
    }
}
