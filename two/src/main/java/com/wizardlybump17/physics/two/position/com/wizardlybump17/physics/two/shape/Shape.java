package com.wizardlybump17.physics.two.position.com.wizardlybump17.physics.two.shape;

import com.wizardlybump17.physics.two.position.Vector2D;
import lombok.NonNull;

public abstract class Shape {

    public abstract @NonNull Vector2D getPosition();

    public abstract double getArea();

    public abstract double getPerimeter();

    public abstract boolean intersects(@NonNull Shape other);
}
