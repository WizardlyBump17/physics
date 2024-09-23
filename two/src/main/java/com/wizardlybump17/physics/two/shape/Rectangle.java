package com.wizardlybump17.physics.two.shape;

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
        return switch (other) {
            case Rectangle rectangle -> {
                Vector2D otherMin = rectangle.getMin();
                Vector2D otherMax = rectangle.getMax();
                yield otherMin.isInAABB(min, max)
                        || Vector2D.isInAABB(otherMin.x() + rectangle.getWidth(), otherMin.y(), min.x(), min.y(), max.x(), max.y())
                        || Vector2D.isInAABB(otherMin.x(), otherMin.y() + rectangle.getHeight(), min.x(), min.y(), max.x(), max.y())
                        || otherMax.isInAABB(min, max);
            }
            default -> throw new IllegalStateException("Unexpected value: " + other);
        };
    }

    public double getHeight() {
        return max.y() - min.y();
    }

    public double getWidth() {
        return max.x() - min.x();
    }
}
