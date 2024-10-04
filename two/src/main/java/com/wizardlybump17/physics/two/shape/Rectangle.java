package com.wizardlybump17.physics.two.shape;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.intersection.rectangle.CircleToRectangleIntersection;
import com.wizardlybump17.physics.two.intersection.rectangle.RectangleToRectangleIntersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.util.MathUtil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
public class Rectangle extends Shape {

    private final @NonNull Vector2D min;
    private final @NonNull Vector2D max;
    private final @NonNull Vector2D position;

    public Rectangle(@NonNull Vector2D min, @NonNull Vector2D max) {
        this.min = Vector2D.min(min, max);
        this.max = Vector2D.max(min, max);
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
            case Circle circle -> {
                Vector2D circlePosition = circle.getPosition();

                double xDistance = Math.abs(position.x() - circlePosition.x());
                double yDistance = Math.abs(position.y() - circlePosition.y());

                double radius = circle.getRadius();

                double width = getWidth() / 2;
                double height = getHeight() / 2;

                if (xDistance > width + radius || yDistance > height + radius)
                    yield false;

                if (xDistance <= width || yDistance <= height)
                    yield true;

                yield MathUtil.square(xDistance - width) + MathUtil.square(yDistance - height) <= MathUtil.square(radius);
            }
            default -> false;
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
            case Rectangle rectangle -> intersects(rectangle) ? new RectangleToRectangleIntersection(this, rectangle) : Intersection.EMPTY;
            case Circle circle -> intersects(circle) ? new CircleToRectangleIntersection(circle, this) : Intersection.EMPTY;
            default -> Intersection.EMPTY;
        };
    }
}