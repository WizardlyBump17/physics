package com.wizardlybump17.physics.two.position.com.wizardlybump17.physics.two.shape;

import com.wizardlybump17.physics.two.position.Vector2D;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class Rectangle extends Shape {

    private final @NonNull Vector2D min;
    private final @NonNull Vector2D max;
    private final @NonNull Vector2D position;

    public Rectangle(@NonNull Vector2D min, @NonNull Vector2D max) {
        this.min = Vector2D.min(min, max);
        this.max = Vector2D.max(max, max);
        position = this.min.midpoint(this.max);
    }

    @Override
    public double getArea() {
        return getHeight() * getWidth();
    }

    @Override
    public double getPerimeter() {
        return (getHeight() + getWidth()) * 2;
    }

    @Override
    public boolean intersects(@NonNull Shape other) {
        return false;
    }

    public double getHeight() {
        return max.y() - min.y();
    }

    public double getWidth() {
        return max.x() - min.x();
    }
}
