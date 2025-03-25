package com.wizardlybump17.physics.two.shape.rotating;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Shape;
import com.wizardlybump17.physics.util.MathUtil;
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
        this.rotation = MathUtil.normalizeRotation(rotation);
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
        RotatingPolygon rotatingPolygon = new RotatingPolygon(
                center,
                points,
                rotation + toAdd
        );
        System.out.println(rotatingPolygon.rotation);
        return rotatingPolygon;
    }

    public static boolean overlaps(@NotNull RotatingPolygon polygon1, @NotNull RotatingPolygon polygon2) {
        return overlapsPolygonToPolygon(polygon1.rotatedPoints, polygon2.rotatedPoints)
                && overlapsPolygonToPolygon(polygon2.rotatedPoints, polygon1.rotatedPoints);
    }

    //Taken from https://github.com/OneLoneCoder/Javidx9/blob/c9ca5d2e5821f2d2e07f07f388803c185a68d13a/PixelGameEngine/SmallerProjects/OneLoneCoder_PGE_PolygonCollisions1.cpp#L140
    public static boolean overlapsPolygonToPolygon(@NotNull List<Vector2D> points1, @NotNull List<Vector2D> points2) {
        for (int pointIndex = 0; pointIndex < points1.size(); pointIndex++) {
            int nextPoint = (pointIndex + 1) % points1.size();
            Vector2D projection = new Vector2D(
                    -(points1.get(nextPoint).y() - points1.get(pointIndex).y()),
                    points1.get(nextPoint).x() - points1.get(pointIndex).x()
            );

            double minR1 = Double.POSITIVE_INFINITY;
            double maxR1 = Double.NEGATIVE_INFINITY;
            for (Vector2D point : points1) {
                double dot = point.dot(projection);
                minR1 = Math.min(minR1, dot);
                maxR1 = Math.max(maxR1, dot);
            }

            double minR2 = Double.POSITIVE_INFINITY;
            double maxR2 = Double.NEGATIVE_INFINITY;
            for (Vector2D point : points2) {
                double dot = point.dot(projection);
                minR2 = Math.min(minR2, dot);
                maxR2 = Math.max(maxR2, dot);
            }

            if (!(maxR2 >= minR1 && maxR1 >= minR2))
                return false;
        }

        return true;
    }

    public double getRotation() {
        return rotation;
    }
}
