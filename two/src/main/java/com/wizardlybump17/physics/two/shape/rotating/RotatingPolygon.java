package com.wizardlybump17.physics.two.shape.rotating;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import com.wizardlybump17.physics.two.shape.Shape;
import com.wizardlybump17.physics.two.util.CollisionsUtil;
import com.wizardlybump17.physics.util.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class RotatingPolygon extends Shape {

    private final @NotNull Vector2D center;
    private final @NotNull List<Vector2D> points;
    private final double rotation;
    private final @NotNull List<Vector2D> transformedPoints;

    public RotatingPolygon(@NotNull Vector2D center, @NotNull List<Vector2D> points, double rotation) {
        this(center, points, rotation, false);
    }

    private RotatingPolygon(@NotNull Vector2D center, @NotNull List<Vector2D> points, double rotation, boolean sorted) {
        if (points.size() < 3)
            throw new IllegalArgumentException("A polygon needs at least three points");

        this.center = center;
        this.points = Collections.unmodifiableList(sorted ? points : sortPoints(points));
        this.rotation = MathUtil.normalizeRotation(rotation);
        this.transformedPoints = this.points.stream()
                .map(point -> point.rotate(rotation).add(center))
                .toList();
    }

    @Override
    public @NotNull Vector2D getPosition() {
        return center;
    }

    @Override
    public double getArea() {
        double area = 0;
        int pointsSize = points.size();
        for (int i = 0; i < pointsSize; i++) {
            Vector2D current = points.get(i);
            Vector2D next = points.get((i + 1) % pointsSize);
            area += ((current.x() * next.y()) - (next.x() * current.y()));
        }
        return Math.abs(area) / 2;
    }

    @Override
    public double getPerimeter() {
        double perimeter = 0;
        int pointsSize = points.size();
        for (int i = 0; i < pointsSize; i++)
            perimeter += points.get(i).distance(points.get((i + 1) % pointsSize));
        return perimeter;
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
                rotation,
                true
        );
    }

    @Override
    public @NotNull Intersection intersect(@NotNull Shape other) {
        return Intersection.EMPTY;
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Vector2D origin) {
        Vector2D closest = null;
        double closestDistance = Double.MAX_VALUE;
        int pointsSize = transformedPoints.size();
        for (int i = 0; i < pointsSize; i++) {
            Vector2D current = transformedPoints.get(i);
            Vector2D next = transformedPoints.get((i + 1) % pointsSize);

            Vector2D attempt = Vector2D.getClosestPointOnLine(current, next, origin);
            double attemptDistance = attempt.distanceSquared(origin);

            if (closestDistance > attemptDistance) {
                closest = attempt;
                closestDistance = attemptDistance;
            }
        }
        return closest == null ? transformedPoints.getFirst() : closest;
    }

    @Override
    public @NotNull Vector2D getClosestPoint(@NotNull Shape shape) {
        return center;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null || getClass() != other.getClass())
            return false;
        RotatingPolygon that = (RotatingPolygon) other;
        return Double.compare(rotation, that.rotation) == 0
                && Objects.equals(center, that.center)
                && Objects.equals(points, that.points)
                && Objects.equals(transformedPoints, that.transformedPoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(center, points, rotation, transformedPoints);
    }

    @Override
    public String toString() {
        return "RotatingPolygon{" +
                "center=" + center +
                ", points=" + points +
                ", rotation=" + rotation +
                ", transformedPoints=" + transformedPoints +
                '}';
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
                rotation + toAdd,
                true
        );
    }

    public double getRotation() {
        return rotation;
    }

    public static @NotNull List<Vector2D> sortPoints(@NotNull List<Vector2D> points) {
        List<Vector2D> result = new ArrayList<>(points.size());

        Vector2D sum = Vector2D.ZERO;
        for (Vector2D point : points)
            sum = sum.add(point);

        Vector2D center = sum.divide(points.size());

        result.addAll(points);
        result.sort(Comparator.comparingDouble(point -> point.angleTo(center)));

        return result;
    }
}
