package com.wizardlybump17.physics.two.shape;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.intersection.rectangle.CircleToCircleIntersection;
import com.wizardlybump17.physics.two.intersection.rectangle.CircleToRectangleIntersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.util.MathUtil;
import lombok.*;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
@RequiredArgsConstructor
public class Circle extends Shape {

    private final @NonNull Vector2D position;
    private final double radius;

    @Override
    public double getArea() {
        return Math.PI * MathUtil.square(radius);
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public boolean intersects(@NonNull Shape other) {
        return switch (other) {
            case Circle otherCircle -> position.distanceSquared(otherCircle.position) < MathUtil.square(radius + otherCircle.radius);
            case Rectangle rectangle -> {
                Vector2D rectanglePosition = rectangle.getPosition();

                double xDistance = Math.abs(position.x() - rectanglePosition.x());
                double yDistance = Math.abs(position.y() - rectanglePosition.y());

                double width = rectangle.getWidth() / 2;
                double height = rectangle.getHeight() / 2;

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
        return point.distanceSquared(position) < MathUtil.square(radius);
    }

    @Override
    public boolean hasPoint(double x, double y) {
        return Vector2D.distanceSquared(x, y, position.x(), position.y()) < MathUtil.square(radius);
    }

    @Override
    public @NonNull Shape at(@NonNull Vector2D position) {
        return new Circle(position, radius);
    }

    @Override
    public @NonNull Intersection intersect(@NonNull Shape other) {
        return switch (other) {
            case Circle otherCircle -> intersects(otherCircle) ? new CircleToCircleIntersection(this, otherCircle) : Intersection.EMPTY;
            case Rectangle rectangle -> intersects(rectangle) ? new CircleToRectangleIntersection(this, rectangle) : Intersection.EMPTY;
            default -> Intersection.EMPTY;
        };
    }
}
