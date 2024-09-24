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

    public Rectangle(@NonNull Vector2D position, double width, double height) {
        this.position = position;
        this.min = position.subtract(width / 2, height / 2);
        this.max = position.add(width / 2, height / 2);
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
                        || otherMax.isInAABB(min, max)
                        || min.isInAABB(otherMin, otherMax)
                        || Vector2D.isInAABB(min.x() + getWidth(), min.y(), otherMin.x(), otherMin.y(), otherMax.x(), otherMax.y())
                        || Vector2D.isInAABB(min.x(), min.y() + getHeight(), otherMin.x(), otherMin.y(), otherMax.x(), otherMax.y())
                        || max.isInAABB(otherMin, otherMax);
            }
            default -> throw new IllegalStateException("Unexpected value: " + other);
        };
    }

    @Override
    public boolean hasPoint(@NonNull Vector2D point) {
        return point.isInAABB(min, max);
    }

    public double getHeight() {
        return max.y() - min.y();
    }

    public double getWidth() {
        return max.x() - min.x();
    }

    @Override
    public @NonNull Shape at(@NonNull Vector2D position) {
        double width = getWidth() / 2;
        double height = getHeight() / 2;
        return new Rectangle(position.subtract(width, height), position.add(width, height));
    }
}
