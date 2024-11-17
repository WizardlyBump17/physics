package com.wizardlybump17.physics.two.shape;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class Shape {

    public abstract @NotNull Vector2D getPosition();

    public abstract double getArea();

    public abstract double getPerimeter();

    public abstract boolean intersects(@NotNull Shape other);

    public abstract boolean hasPoint(@NotNull Vector2D point);

    public abstract boolean hasPoint(double x, double y);

    public abstract @NotNull Shape at(@NotNull Vector2D position);

    public abstract @NotNull Intersection intersect(@NotNull Shape other);

    public abstract @NotNull Vector2D getClosestPoint(@NotNull Vector2D origin);

    public abstract @NotNull Vector2D getClosestPoint(@NotNull Shape shape);

    @Override
    public abstract boolean equals(@Nullable Object other);

    @Override
    public abstract int hashCode();

    @Override
    public abstract String toString();
}
