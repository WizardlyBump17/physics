package com.wizardlybump17.physics.util;

public final class MathUtil {

    public static final double EPSILON = 0.000001;

    private MathUtil() {
    }

    public static double square(double x) {
        return x * x;
    }

    public static double normalizeRotation(double yaw) {
        yaw %= 360;
        if (yaw < 0)
            yaw += 360;
        return yaw;
    }
}
