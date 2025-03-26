package com.wizardlybump17.physics.two.shape;

import com.wizardlybump17.physics.two.Line;
import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.intersection.circle.CircleToRectangleIntersection;
import com.wizardlybump17.physics.two.intersection.rectangle.RectangleToRectangleIntersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.rotating.RotatingPolygon;
import com.wizardlybump17.physics.two.util.CollisionsUtil;
import com.wizardlybump17.physics.util.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Rectangle extends Shape {

    private final @NotNull Vector2D min;
    private final @NotNull Vector2D max;
    private final @NotNull Vector2D position;

    public Rectangle(@NotNull Vector2D min, @NotNull Vector2D max) {
        this.min = Vector2D.min(min, max);
        this.max = Vector2D.max(min, max);
        position = this.min.midpoint(this.max);
    }

    public Rectangle(@NotNull Vector2D position, double width, double height) {
        this.position = position;
        this.min = position.subtract(width / 2, height / 2);
        this.max = position.add(width / 2, height / 2);
    }

    public @NotNull Vector2D getMin() {
        return min;
    }

    public @NotNull Vector2D getMax() {
        return max;
    }

    @Override
    public @NotNull Vector2D getPosition() {
        return position;
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
    public boolean intersects(@NotNull Shape other) {
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
            case RotatingPolygon polygon -> CollisionsUtil.overlapsPolygonToRectangle(polygon, this);
            default -> false;
        };
    }

    @Override
    public boolean hasPoint(@NotNull Vector2D point) {
        return point.isInAABB(min, max);
    }

    @Override
    public boolean hasPoint(double x, double y) {
        return Vector2D.isInAABB(x, y, min.x(), min.y(), max.x(), max.y());
    }

    public double getHeight() {
        return max.y() - min.y();
    }

    public double getWidth() {
        return max.x() - min.x();
    }

    @Override
    public @NotNull Shape at(@NotNull Vector2D position) {
        double width = getWidth() / 2;
        double height = getHeight() / 2;
        return new Rectangle(position.subtract(width, height), position.add(width, height));
    }

    @Override
    public @NotNull Intersection intersect(@NotNull Shape other) {
        return switch (other) {
            case Rectangle rectangle -> intersects(rectangle) ? new RectangleToRectangleIntersection(this, rectangle) : Intersection.EMPTY;
            case Circle circle -> intersects(circle) ? new CircleToRectangleIntersection(circle, this) : Intersection.EMPTY;
            default -> Intersection.EMPTY;
        };
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Vector2D origin) {
        if (hasPoint(origin)) {
            double leftDistance = origin.x() - min.x();
            double rightDistance = max.x() - origin.x();
            double bottomDistance = origin.y() - min.y();
            double topDistance = max.y() - origin.y();

            double minHorizontalDistance = Math.min(leftDistance, rightDistance);
            double minVerticalDistance = Math.min(bottomDistance, topDistance);

            if (minHorizontalDistance < minVerticalDistance)
                return new Vector2D(leftDistance < rightDistance ? min.x() : max.x(), origin.y());
            return new Vector2D(origin.x(), bottomDistance < topDistance ? min.y() : max.y());
        }

        return new Vector2D(
                Math.clamp(origin.x(), min.x(), max.x()),
                Math.clamp(origin.y(), min.y(), max.y())
        );
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Shape shape) {
        return getClosestPoint(shape.getPosition());
    }

    public @NotNull Line getUpperLine() {
        return new Line(min.y(max.y()), max);
    }

    public @NotNull Line getRightLine() {
        return new Line(max, min.x(max.x()));
    }

    public @NotNull Line getLowerLine() {
        return new Line(min.x(max.x()), min);
    }

    public @NotNull Line getLeftLine() {
        return new Line(min, min.y(max.y()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Rectangle rectangle = (Rectangle) o;
        return Objects.equals(min, rectangle.min) && Objects.equals(max, rectangle.max) && Objects.equals(position, rectangle.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(min, max, position);
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "min=" + min +
                ", max=" + max +
                ", position=" + position +
                '}';
    }
}