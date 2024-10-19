package com.wizardlybump17.physics.two.shape;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public final class EmptyShape extends Shape {

    public static final @NotNull EmptyShape INSTANCE = new EmptyShape();

    private EmptyShape() {
    }

    @Override
    public @NonNull Vector2D getPosition() {
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
    public boolean intersects(@NonNull Shape other) {
        return false;
    }

    @Override
    public boolean hasPoint(@NonNull Vector2D point) {
        return false;
    }

    @Override
    public boolean hasPoint(double x, double y) {
        return false;
    }

    @Override
    public @NonNull Shape at(@NonNull Vector2D position) {
        return INSTANCE;
    }

    @Override
    public @NonNull Intersection intersect(@NonNull Shape other) {
        return Intersection.EMPTY;
    }
}
