package com.wizardlybump17.physics.two.shape.rotating;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RotatingPolygon extends Shape {

    private final @NotNull Vector2D center;
    private final @NotNull List<Vector2D> points;
    private final double rotation;
    private final @NotNull List<Vector2D> rotatedPoints;

    public RotatingPolygon(@NotNull Vector2D center, @NotNull List<Vector2D> points, double rotation) {
        this.center = center;
        this.points = points;
        this.rotation = rotation;
        this.rotatedPoints = points.stream()
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
            case RotatingPolygon otherPolygon -> overlaps(this, otherPolygon);
//            case Circle circle -> {
//
//            }
            default -> false;
        };
    }

    @Override
    public boolean hasPoint(@NotNull Vector2D point) {
        return hasPoint(point.x(), point.y());
    }

    @Override
    public boolean hasPoint(double x, double y) {
//        return true;
        return RotatingPolygon.overlaps(this, new RotatingPolygon(new Vector2D(x, y), List.of(new Vector2D(-0.1, -0.1), new Vector2D(0.1, 0.1)), 0));
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

    public @NotNull List<Vector2D> getRotatedPoints() {
        return rotatedPoints;
    }

    public @NotNull RotatingPolygon addRotation(double toAdd) {
        System.out.println(rotation + toAdd);
        return new RotatingPolygon(
                center,
                points,
                rotation + toAdd
        );
    }

    public static boolean overlaps(@NotNull RotatingPolygon poly1, @NotNull RotatingPolygon poly2) {
        return overlaps0(poly1, poly2) && overlaps0(poly2, poly1);
    }

    protected static boolean overlaps0(@NotNull RotatingPolygon polygon1, @NotNull RotatingPolygon polygon2) {
        for (int pointIndex = 0; pointIndex < polygon1.rotatedPoints.size(); pointIndex++) {
            int nextPoint = (pointIndex + 1) % polygon1.rotatedPoints.size();
            Vector2D projection = new Vector2D(
                    -(polygon1.rotatedPoints.get(nextPoint).y() - polygon1.rotatedPoints.get(pointIndex).y()),
                    polygon1.rotatedPoints.get(nextPoint).x() - polygon1.rotatedPoints.get(pointIndex).x()
            );

            double minR1 = Double.POSITIVE_INFINITY;
            double maxR1 = Double.NEGATIVE_INFINITY;
            for (Vector2D point : polygon1.rotatedPoints) {
                double dot = point.dot(projection);
                minR1 = Math.min(minR1, dot);
                maxR1 = Math.max(maxR1, dot);
            }

            double minR2 = Double.POSITIVE_INFINITY;
            double maxR2 = Double.NEGATIVE_INFINITY;
            for (Vector2D point : polygon2.rotatedPoints) {
                double dot = point.dot(projection);
                minR2 = Math.min(minR2, dot);
                maxR2 = Math.max(maxR2, dot);
            }

            if (!(maxR2 >= minR1 && maxR1 >= minR2))
                return false;
        }

        return true;
    }
}
