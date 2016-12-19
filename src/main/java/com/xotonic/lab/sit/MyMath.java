package com.xotonic.lab.sit;

/**
 * Вспомогательный класс с математическими операциями
 */
public class MyMath {

    /**
     * Заключить число в интервал
     */
    public static float clamp(float min, float value, float max) {
        return Math.max(min, Math.min(max, value));
    }

    /** "Отразить" число от границ интервала */
    public static float reflect(float min, float value, float max, float step) {
        if (value + step >= max) {
            return max - Math.abs(step - Math.abs(max - value));
        }
        if (value + step <= min) {
            return min + Math.abs(step - Math.abs(value - min));
        }
        return value + step;
    }
}
