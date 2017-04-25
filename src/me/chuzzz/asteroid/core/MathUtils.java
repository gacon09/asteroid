package me.chuzzz.asteroid.core;

public class MathUtils {
    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public static int randomBetween(int min, int max) {
        return (int) (Math.floor(Math.random() * (max - min + 1) + min));
    }
}
