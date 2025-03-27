package com.wizardlybump17.physics.two.shape.rotating;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import com.wizardlybump17.physics.two.shape.Shape;
import com.wizardlybump17.physics.two.util.CollisionsUtil;
import com.wizardlybump17.physics.util.MathUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public class RotatingPolygon extends Shape {

    private final @NotNull Vector2D center;
    private final @NotNull List<Vector2D> points;
    private final double rotation;
    private final @NotNull List<Vector2D> transformedPoints;

    public RotatingPolygon(@NotNull Vector2D center, @NotNull List<Vector2D> points, double rotation) {
        this.center = center;
        this.points = Collections.unmodifiableList(points);
        this.rotation = MathUtil.normalizeRotation(rotation);
        this.transformedPoints = points.stream()
                .map(point -> point.rotate(rotation).add(center))
                .toList();
    }

    @Override
    public @NotNull Vector2D getPosition() {
        return center;
    }

    @Override
    public double getArea() {
        return 0;
    }

    @Override
    public double getPerimeter() {
        return 0;
    }

    @Override
    public boolean intersects(@NotNull Shape other) {
        return switch (other) {
            case RotatingPolygon otherPolygon -> CollisionsUtil.overlapsPolygonToPolygon(this, otherPolygon);
            case Rectangle rectangle -> CollisionsUtil.overlapsPolygonToRectangle(this, rectangle);
            case Circle circle -> CollisionsUtil.overlapsPolygonToCircle(this, circle);
            default -> false;
        };
    }

    @Override
    public boolean hasPoint(@NotNull Vector2D point) {
        return hasPoint(point.x(), point.y());
    }

    @Override
    public boolean hasPoint(double x, double y) {
        return CollisionsUtil.isPointInsidePolygon(transformedPoints, x, y);
    }

    @Override
    public @NotNull Shape at(@NotNull Vector2D position) {
        return new RotatingPolygon(
                position,
                points,
                rotation
        );
    }

    @Override
    public @NotNull Intersection intersect(@NotNull Shape other) {
        return Intersection.EMPTY;
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Vector2D origin) {
        return origin;
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Shape shape) {
        return center;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "";
    }

    public @NotNull List<Vector2D> getPoints() {
        return points;
    }

    public @NotNull List<Vector2D> getTransformedPoints() {
        return transformedPoints;
    }

    public @NotNull RotatingPolygon addRotation(double toAdd) {
        return new RotatingPolygon(
                center,
                points,
                rotation + toAdd
        );
    }

    public double getRotation() {
        return rotation;
    }
}
