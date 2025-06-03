package com.wizardlybump17.physics.three;

import com.wizardlybump17.physics.util.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public record Vector3D(double x, double y, double z) {

    public static final @NotNull Vector3D ZERO = new Vector3D(0, 0, 0);

    public @NotNull Vector3D add(double x, double y, double z) {
        return new Vector3D(this.x + x, this.y + y, this.z + z);
    }

    public @NotNull Vector3D add(double value) {
        return add(value, value, value);
    }

    public @NotNull Vector3D add(@NotNull Vector3D other) {
        return add(other.x, other.y, other.z);
    }

    public @NotNull Vector3D subtract(double x, double y, double z) {
        return new Vector3D(this.x - x, this.y - y, this.z - z);
    }

    public @NotNull Vector3D subtract(double value) {
        return subtract(value, value, value);
    }

    public @NotNull Vector3D subtract(@NotNull Vector3D other) {
        return subtract(other.x, other.y, other.z);
    }

    public @NotNull Vector3D multiply(double x, double y, double z) {
        return new Vector3D(this.x * x, this.y * y, this.z * z);
    }

    public @NotNull Vector3D multiply(double value) {
        return multiply(value, value, value);
    }

    public @NotNull Vector3D multiply(@NotNull Vector3D other) {
        return multiply(other.x, other.y, other.z);
    }

    public @NotNull Vector3D divide(double x, double y, double z) {
        return new Vector3D(this.x / x, this.y / y, this.z / z);
    }

    public @NotNull Vector3D divide(double value) {
        return divide(value, value, value);
    }

    public @NotNull Vector3D divide(@NotNull Vector3D other) {
        return divide(other.x, other.y, other.z);
    }

    public @NotNull Vector3D withX(double x) {
        return new Vector3D(x, this.y, this.z);
    }

    public @NotNull Vector3D withY(double y) {
        return new Vector3D(this.x, y, this.z);
    }

    public @NotNull Vector3D withZ(double z) {
        return new Vector3D(this.x, this.y, z);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        return MathUtil.square(x) + MathUtil.square(y) + MathUtil.square(z);
    }

    /**
     * @return if this vector {@link #lengthSquared()} is less or equals to {@link MathUtil#EPSILON}
     */
    public boolean isZero() {
        return lengthSquared() <= MathUtil.EPSILON;
    }

    public boolean isInAABB(@NotNull Vector3D min, @NotNull Vector3D max) {
        return isInAABB(x, y, z, min.x, min.y, min.z, max.x, max.y, max.z);
    }

    public static boolean isInAABB(double x, double y, double z, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        return x >= Math.min(minX, maxX) && x <= Math.max(minX, maxX) &&
                y >= Math.min(minY, maxY) && y <= Math.max(minY, maxY) &&
                z >= Math.min(minZ, maxZ) && z <= Math.max(minZ, maxZ);
    }

    public double distanceSquared(double otherX, double otherY, double otherZ) {
        return distanceSquared(x, y, z, otherX, otherY, otherZ);
    }

    public double distanceSquared(@NotNull Vector3D other) {
        return distanceSquared(this, other);
    }

    public static double distanceSquared(double x, double y, double z, double otherX, double otherY, double otherZ) {
        return MathUtil.square(x - otherX) + MathUtil.square(y - otherY) + MathUtil.square(z - otherZ);
    }

    public static double distanceSquared(@NotNull Vector3D a, @NotNull Vector3D b) {
        return distanceSquared(a.x, a.y, a.z, b.x, b.y, b.z);
    }

    public double distance(double otherX, double otherY, double otherZ) {
        return distance(x, y, z, otherX, otherY, otherZ);
    }

    public double distance(@NotNull Vector3D other) {
        return distance(this, other);
    }

    public static double distance(double x, double y, double z, double otherX, double otherY, double otherZ) {
        return Math.sqrt(distanceSquared(x, y, z, otherX, otherY, otherZ));
    }

    public static double distance(@NotNull Vector3D a, @NotNull Vector3D b) {
        return distance(a.x, a.y, a.z, b.x, b.y, b.z);
    }

    public static @NotNull Vector3D randomVector(@NotNull Random random, double minX, double maxX, double minY, double maxY, double minZ, double maxZ) {
        return new Vector3D(
                random.nextDouble(Math.min(minX, maxX), Math.max(minX, maxX)),
                random.nextDouble(Math.min(minY, maxY), Math.max(minY, maxY)),
                random.nextDouble(Math.min(minZ, maxZ), Math.max(minZ, maxZ))
        );
    }

    public static @NotNull Vector3D randomVector(@NotNull Random random, @NotNull Vector3D min, @NotNull Vector3D max) {
        return randomVector(random, min.x, max.x, min.y, max.y, min.z, max.z);
    }

    public @NotNull Vector3D normalize() {
        if (isZero())
            return ZERO;
        return divide(length());
    }

    public @NotNull Vector3D facing(@NotNull Vector3D other) {
        return subtract(other);
    }

    public static @NotNull Vector3D min(@NotNull Vector3D a, @NotNull Vector3D b) {
        return new Vector3D(
                Math.min(a.x, b.x),
                Math.min(a.y, b.y),
                Math.min(a.z, b.z)
        );
    }

    public static @NotNull Vector3D max(@NotNull Vector3D a, @NotNull Vector3D b) {
        return new Vector3D(
                Math.max(a.x, b.x),
                Math.max(a.y, b.y),
                Math.max(a.z, b.z)
        );
    }

    public @NotNull Vector3D midpoint(@NotNull Vector3D other) {
        return add(other).divide(2);
    }

    public @NotNull Vector3D rotateAroundX(double radians) {
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        return new Vector3D(
                x,
                cos * y - sin * z,
                sin * y + cos * z
        );
    }

    public @NotNull Vector3D rotateAroundY(double radians) {
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        return new Vector3D(
                cos * x + sin * z,
                y,
                -sin * x + cos * z
        );
    }

    public @NotNull Vector3D rotateAroundZ(double radians) {
        double cos = Math.cos(radians);
        double sin = Math.sin(radians);

        return new Vector3D(
                cos * x - sin * y,
                sin * x + cos * y,
                z
        );
    }

    public @NotNull Vector3D rotateAround(@NotNull Vector3D angles) {
        return rotateAroundX(Math.toRadians(angles.x))
                .rotateAroundY(Math.toRadians(angles.y))
                .rotateAroundZ(Math.toRadians(angles.z));
    }
}
