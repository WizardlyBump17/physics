package com.wizardlybump17.physics.two.position;

import com.wizardlybump17.physics.two.iterator.Vector2DIterator;
import com.wizardlybump17.physics.util.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public record Vector2D(double x, double y) {

    public static final @NotNull Vector2D ZERO = new Vector2D(0, 0);

    public @NotNull Vector2D add(double x, double y) {
        return new Vector2D(this.x + x, this.y + y);
    }

    public @NotNull Vector2D add(@NotNull Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public @NotNull Vector2D subtract(double x, double y) {
        return new Vector2D(this.x - x, this.y - y);
    }

    public @NotNull Vector2D subtract(@NotNull Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public @NotNull Vector2D midpoint(double x, double y) {
        return new Vector2D((this.x + x) / 2, (this.y + y) / 2);
    }

    public @NotNull Vector2D midpoint(@NotNull Vector2D other) {
        return new Vector2D((x + other.x) / 2, (y + other.y) / 2);
    }

    public static @NotNull Vector2D min(@NotNull Vector2D a, @NotNull Vector2D b) {
        return new Vector2D(Math.min(a.x, b.x), Math.min(a.y, b.y));
    }

    public static @NotNull Vector2D max(@NotNull Vector2D a, @NotNull Vector2D b) {
        return new Vector2D(Math.max(a.x, b.x), Math.max(a.y, b.y));
    }

    public boolean isInAABB(@NotNull Vector2D min, @NotNull Vector2D max) {
        return isInAABB(x, y, min.x, min.y, max.x, max.y);
    }

    public static boolean isInAABB(double x, double y, double minX, double minY, double maxX, double maxY) {
        double realMinX = Math.min(minX, maxX);
        double realMinY = Math.min(minY, maxY);
        double realMaxX = Math.max(minX, maxX);
        double realMaxY = Math.max(minY, maxY);
        return x >= realMinX && x <= realMaxX
                && y >= realMinY && y <= realMaxY;
    }

    public double length() {
        return Math.sqrt(x * x + y * y);
    }

    public double lengthSquared() {
        return x * x + y * y;
    }

    public @NotNull Vector2D normalize() {
        double length = length();
        return new Vector2D(x / length, y / length);
    }

    public double distance(@NotNull Vector2D other) {
        return Math.sqrt(MathUtil.square(x - other.x) + MathUtil.square(y - other.y));
    }

    public double distanceSquared(@NotNull Vector2D other) {
        return MathUtil.square(x - other.x) + MathUtil.square(y - other.y);
    }

    public @NotNull Vector2D multiply(@NotNull Vector2D other) {
        return new Vector2D(x * other.x, y * other.y);
    }

    public @NotNull Vector2D multiply(double x, double y) {
        return new Vector2D(this.x * x, this.y * y);
    }

    public @NotNull Vector2D multiply(double multiplier) {
        return new Vector2D(x * multiplier, y * multiplier);
    }

    public @NotNull Vector2D divide(@NotNull Vector2D other) {
        return new Vector2D(x / other.x, y / other.y);
    }

    public @NotNull Vector2D divide(double x, double y) {
        return new Vector2D(this.x / x, this.y / y);
    }

    public @NotNull Vector2D divide(double divisor) {
        return new Vector2D(x / divisor, y / divisor);
    }

    public @NotNull Vector2D x(double x) {
        return new Vector2D(x, this.y);
    }

    public @NotNull Vector2D y(double y) {
        return new Vector2D(this.x, y);
    }

    public @NotNull Vector2DIterator iterateTowards(@NotNull Vector2D other, double divisor) {
        return new Vector2DIterator(this, other, divisor);
    }

    public static @NotNull Vector2D randomVector(@NotNull Random random, double minX, double minY, double maxX, double maxY) {
        return new Vector2D(
                random.nextDouble(minX, maxX),
                random.nextDouble(minY, maxY)
        );
    }

    public static @NotNull Vector2D randomVector(@NotNull Random random, @NotNull Vector2D min, @NotNull Vector2D max) {
        return new Vector2D(
                random.nextDouble(Math.min(min.x, max.x), Math.max(min.x, max.x)),
                random.nextDouble(Math.max(min.y, max.y), Math.max(min.y, max.y))
        );
    }

    public static double distanceSquared(double x, double y, double otherX, double otherY) {
        return MathUtil.square(x - otherX) + MathUtil.square(y - otherY);
    }

    public double angleTo(@NotNull Vector2D other) {
        return Math.atan2(other.y() - y, other.x() - x);
    }

    public double dot(@NotNull Vector2D other) {
        return (x * other.x) + (y * other.y);
    }

    /**
     * @return if this vector {@link #lengthSquared()} is less or equals to {@link MathUtil#EPSILON}
     */
    public boolean zero() {
        return lengthSquared() <= MathUtil.EPSILON;
    }

    public @NotNull Vector2D rotate(double angle) {
        angle = Math.toRadians(angle);
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        return new Vector2D(
                (x * cos) - (y * sin),
                (x * sin) + (y * cos)
        );
    }
}
