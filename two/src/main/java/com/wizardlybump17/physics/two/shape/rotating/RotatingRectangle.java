package com.wizardlybump17.physics.two.shape.rotating;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RotatingRectangle extends Shape {

    private final @NotNull Vector2D position;
    private final double width;
    private final double height;
    private final double rotation;

    public RotatingRectangle(@NotNull Vector2D position, double width, double height, double rotation) {
        this.position = position;
        this.width = width;
        this.height = height;
        this.rotation = rotation;
    }

    public RotatingRectangle(@NotNull Vector2D position, double width, double height) {
        this(position, width, height, 0);
    }

    @Override
    public @NotNull Vector2D getPosition() {
        return position;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return width * 2 + height * 2;
    }

    @Override
    public boolean intersects(@NotNull Shape other) {
        return false;
    }

    @Override
    public boolean hasPoint(@NotNull Vector2D point) {
        return false;
    }

    @Override
    public boolean hasPoint(double x, double y) {
        return false;
    }

    @Override
    public @NotNull RotatingRectangle at(@NotNull Vector2D position) {
        return new RotatingRectangle(
                position,
                width,
                height,
                rotation
        );
    }

    @Override
    public @NotNull Intersection intersect(@NotNull Shape other) {
        return Intersection.EMPTY;
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Vector2D origin) {
        return origin;
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Shape shape) {
        return position;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }

    public @NotNull RotatingRectangle addRotation(double toAdd) {
        return new RotatingRectangle(
                position,
                width,
                height,
                rotation + toAdd
        );
    }

    public @NotNull RotatingRectangle removeRotation(double toRemove) {
        return addRotation(-toRemove);
    }

    public @NotNull RotatingRectangle withRotation(double newRotation) {
        return new RotatingRectangle(
                position,
                width,
                height,
                newRotation
        );
    }
}
