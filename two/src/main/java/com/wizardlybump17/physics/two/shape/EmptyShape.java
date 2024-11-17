package com.wizardlybump17.physics.two.shape;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class EmptyShape extends Shape {

    public static final @NotNull EmptyShape INSTANCE = new EmptyShape();

    private EmptyShape() {
    }

    @Override
    public @NotNull Vector2D getPosition() {
        return Vector2D.ZERO;
    }

    @Override
    public double getArea() {
        return 0;
    }

    @Override
    public double getPerimeter() {
        return 0;
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
    public @NotNull Shape at(@NotNull Vector2D position) {
        return INSTANCE;
    }

    @Override
    public @NotNull Intersection intersect(@NotNull Shape other) {
        return Intersection.EMPTY;
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Vector2D origin) {
        return Vector2D.ZERO;
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Shape shape) {
        return Vector2D.ZERO;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return other instanceof EmptyShape;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "EmptyShape";
    }
}
