package com.wizardlybump17.physics.two.shape;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.intersection.circle.CircleToCircleIntersection;
import com.wizardlybump17.physics.two.intersection.rectangle.RectangleToCircleIntersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.rotating.RotatingPolygon;
import com.wizardlybump17.physics.two.util.CollisionsUtil;
import com.wizardlybump17.physics.util.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Circle extends Shape {

    private final @NotNull Vector2D position;
    private final double radius;

    public Circle(@NotNull Vector2D position, double radius) {
        this.position = position;
        this.radius = radius;
    }

    @Override
    public @NotNull Vector2D getPosition() {
        return position;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public double getArea() {
        return Math.PI * MathUtil.square(radius);
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public boolean intersects(@NotNull Shape other) {
        return switch (other) {
            case Circle otherCircle -> CollisionsUtil.overlapsCircleToCircle(this, otherCircle);
            case Rectangle rectangle -> CollisionsUtil.overlapsRectangleToCircle(rectangle, this);
            case RotatingPolygon polygon -> CollisionsUtil.overlapsPolygonToCircle(polygon, this);
            default -> false;
        };
    }

    @Override
    public boolean hasPoint(@NotNull Vector2D point) {
        return point.distanceSquared(position) < MathUtil.square(radius);
    }

    @Override
    public boolean hasPoint(double x, double y) {
        return Vector2D.distanceSquared(x, y, position.x(), position.y()) < MathUtil.square(radius);
    }

    @Override
    public @NotNull Shape at(@NotNull Vector2D position) {
        return new Circle(position, radius);
    }

    @Override
    public @NotNull Intersection intersect(@NotNull Shape other) {
        return switch (other) {
            case Circle otherCircle -> intersects(otherCircle) ? new CircleToCircleIntersection(this, otherCircle) : Intersection.EMPTY;
            case Rectangle rectangle -> intersects(rectangle) ? new RectangleToCircleIntersection(this, rectangle) : Intersection.EMPTY;
            default -> Intersection.EMPTY;
        };
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Vector2D origin) {
        double angle = position.angleTo(origin);
        return position.add(Math.cos(angle) * radius, Math.sin(angle) * radius);
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Shape shape) {
        return switch (shape) {
            case Circle circle -> getClosestPoint(circle.getPosition());
            default -> Vector2D.ZERO;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Circle circle = (Circle) o;
        return Double.compare(radius, circle.radius) == 0 && Objects.equals(position, circle.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(position, radius);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "position=" + position +
                ", radius=" + radius +
                '}';
    }
}
