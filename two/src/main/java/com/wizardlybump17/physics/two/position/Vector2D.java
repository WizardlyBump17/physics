package com.wizardlybump17.physics.two.position;

import lombok.NonNull;

public record Vector2D(double x, double y) {

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
        return realMinX >= x && x <= realMaxX
                && realMinY >= y && y <= realMaxY;
    }
}
