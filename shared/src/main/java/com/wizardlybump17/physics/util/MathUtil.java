package com.wizardlybump17.physics.util;

public final class MathUtil {

    public static final double EPSILON = 0.000001;

    private MathUtil() {
    }

    public static double square(double x) {
        return x * x;
    }

    public static double cube(double number) {
        return number * number * number;
    }
}
