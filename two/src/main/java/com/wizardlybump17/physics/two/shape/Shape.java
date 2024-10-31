package com.wizardlybump17.physics.two.shape;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public abstract class Shape {

    public abstract @NonNull Vector2D getPosition();

    public abstract double getArea();

    public abstract double getPerimeter();

    public abstract boolean intersects(@NonNull Shape other);

    public abstract boolean hasPoint(@NonNull Vector2D point);

    public abstract boolean hasPoint(double x, double y);

    public abstract @NonNull Shape at(@NonNull Vector2D position);

    public abstract @NonNull Intersection intersect(@NonNull Shape other);

    public abstract @NotNull Vector2D getClosestPoint(@NotNull Vector2D origin);
}
