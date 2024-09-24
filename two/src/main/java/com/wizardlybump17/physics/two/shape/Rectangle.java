package com.wizardlybump17.physics.two.shape;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.intersection.rectangle.RectangleToRectangleIntersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
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
                yield min.x() < otherMax.x() && max.x() > otherMin.x()
                        && min.y() < otherMax.y() && max.y() > otherMin.y();
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

    @Override
    public @NonNull Intersection intersect(@NonNull Shape other) {
        return switch (other) {
            case Rectangle rectangle -> {
                Vector2D otherMin = rectangle.getMin();
                Vector2D otherMax = rectangle.getMax();

                double minX = Math.min(max.x(), otherMax.x());
                double minY = Math.min(max.y(), otherMax.y());
                double maxX = Math.max(min.x(), otherMin.x());
                double maxY = Math.max(min.y(), otherMin.y());

                if (minY < maxY || minX < maxX)
                    yield Intersection.EMPTY;

                yield new RectangleToRectangleIntersection(
                        this,
                        rectangle,
                        new Rectangle(
                                new Vector2D(maxX, maxY),
                                new Vector2D(minX, minY)
                        )
                );
            }
            default -> Intersection.EMPTY;
        };
    }
}
