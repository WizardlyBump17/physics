package com.wizardlybump17.physics.two.position;

import com.wizardlybump17.physics.two.iterator.Vector2DIterator;
import com.wizardlybump17.physics.two.util.MathUtil;
import lombok.NonNull;

import java.util.Random;

public record Vector2D(double x, double y) {

    public static final @NonNull Vector2D ZERO = new Vector2D(0, 0);

    public @NonNull Vector2D add(double x, double y) {
        return new Vector2D(this.x + x, this.y + y);
    }

    public @NonNull Vector2D add(@NonNull Vector2D other) {
        return new Vector2D(x + other.x, y + other.y);
    }

    public @NonNull Vector2D subtract(double x, double y) {
        return new Vector2D(this.x - x, this.y - y);
    }

    public @NonNull Vector2D subtract(@NonNull Vector2D other) {
        return new Vector2D(x - other.x, y - other.y);
    }

    public @NonNull Vector2D midpoint(double x, double y) {
        return new Vector2D((this.x + x) / 2, (this.y + y) / 2);
    }

    public @NonNull Vector2D midpoint(@NonNull Vector2D other) {
        return new Vector2D((x + other.x) / 2, (y + other.y) / 2);
    }

    public static @NonNull Vector2D min(@NonNull Vector2D a, @NonNull Vector2D b) {
        return new Vector2D(Math.min(a.x, b.x), Math.min(a.y, b.y));
    }

    public static @NonNull Vector2D max(@NonNull Vector2D a, @NonNull Vector2D b) {
        return new Vector2D(Math.max(a.x, b.x), Math.max(a.y, b.y));
    }

    public boolean isInAABB(@NonNull Vector2D min, @NonNull Vector2D max) {
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

    public @NonNull Vector2D normalize() {
        double length = length();
        return new Vector2D(x / length, y / length);
    }

    public double distance(@NonNull Vector2D other) {
        return Math.sqrt(MathUtil.square(x - other.x) + MathUtil.square(y - other.y));
    }

    public double distanceSquared(@NonNull Vector2D other) {
        return MathUtil.square(x - other.x) + MathUtil.square(y - other.y);
    }

    public @NonNull Vector2D multiply(@NonNull Vector2D other) {
        return new Vector2D(x * other.x, y * other.y);
    }

    public @NonNull Vector2D multiply(double x, double y) {
        return new Vector2D(this.x * x, this.y * y);
    }

    public @NonNull Vector2D multiply(double multiplier) {
        return new Vector2D(x * multiplier, y * multiplier);
    }

    public @NonNull Vector2D divide(@NonNull Vector2D other) {
        return new Vector2D(x / other.x, y / other.y);
    }

    public @NonNull Vector2D divide(double x, double y) {
        return new Vector2D(this.x / x, this.y / y);
    }

    public @NonNull Vector2D divide(double divisor) {
        return new Vector2D(x / divisor, y / divisor);
    }

    public @NonNull Vector2D x(double x) {
        return new Vector2D(x, this.y);
    }

    public @NonNull Vector2D y(double y) {
        return new Vector2D(this.x, y);
    }

    public @NonNull Vector2DIterator iterateTowards(@NonNull Vector2D other, double divisor) {
        return new Vector2DIterator(this, other, divisor);
    }

    public static @NonNull Vector2D randomVector(@NonNull Random random, double minX, double minY, double maxX, double maxY) {
        return new Vector2D(
                random.nextDouble(minX, maxX),
                random.nextDouble(minY, maxY)
        );
    }

    public static @NonNull Vector2D randomVector(@NonNull Random random, @NonNull Vector2D min, @NonNull Vector2D max) {
        return new Vector2D(
                random.nextDouble(Math.min(min.x, max.x), Math.max(min.x, max.x)),
                random.nextDouble(Math.max(min.y, max.y), Math.max(min.y, max.y))
        );
    }

    public static double distanceSquared(double x, double y, double otherX, double otherY) {
        return MathUtil.square(x - otherX) + MathUtil.square(y - otherY);
    }

    public double angleTo(@NonNull Vector2D other) {
        return Math.atan2(other.y() - y, other.x() - x);
    }
}
