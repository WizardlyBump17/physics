package com.wizardlybump17.physics.two.shape.rotating;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
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
            case Circle circle -> collidesWith(rotatedPoints, circle);
            case Rectangle rectangle -> overlaps(this, rectangle);
            default -> false;
        };
    }

    @Override
    public boolean hasPoint(@NotNull Vector2D point) {
        return hasPoint(point.x(), point.y());
    }

    @Override
    public boolean hasPoint(double x, double y) {
        return hasPoint(rotatedPoints, x, y);
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
        return new RotatingPolygon(
                center,
                points,
                rotation + toAdd
        );
    }

    public static boolean overlaps(@NotNull RotatingPolygon polygon1, @NotNull RotatingPolygon polygon2) {
        return overlapsPolygonToPolygon(polygon1.rotatedPoints, polygon2.rotatedPoints)
                && overlapsPolygonToPolygon(polygon2.rotatedPoints, polygon1.rotatedPoints);
    }

    public static boolean overlaps(@NotNull RotatingPolygon polygon, @NotNull Rectangle rectangle) {
        double rectangleHeight = rectangle.getHeight();
        Vector2D rectangleMin = rectangle.getMin();
        Vector2D rectangleMax = rectangle.getMax();

        List<Vector2D> rectanglePoints = List.of(
                rectangleMin,
                rectangleMin.add(0, rectangleHeight),
                rectangleMax,
                rectangleMax.subtract(0, rectangleHeight)
        );
        return overlapsPolygonToPolygon(rectanglePoints, polygon.rotatedPoints) && overlapsPolygonToPolygon(polygon.rotatedPoints, rectanglePoints);
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

    public static boolean hasPoint(@NotNull List<Vector2D> points, double xToCheck, double yToCheck) {
        for (int pointIndex = 0; pointIndex < points.size(); pointIndex++) {
            int nextPoint = (pointIndex + 1) % points.size();
            Vector2D projection = new Vector2D(
                    -(points.get(nextPoint).y() - points.get(pointIndex).y()),
                    points.get(nextPoint).x() - points.get(pointIndex).x()
            );

            double minR1 = Double.POSITIVE_INFINITY;
            double maxR1 = Double.NEGATIVE_INFINITY;
            for (Vector2D point : points) {
                double dot = point.dot(projection);
                minR1 = Math.min(minR1, dot);
                maxR1 = Math.max(maxR1, dot);
            }

            double minR2 = Double.POSITIVE_INFINITY;
            double maxR2 = Double.NEGATIVE_INFINITY;
            double dot = Vector2D.dot(projection.x(), projection.y(), xToCheck, yToCheck);
            minR2 = Math.min(minR2, dot);
            maxR2 = Math.max(maxR2, dot);

            if (!(maxR2 >= minR1 && maxR1 >= minR2))
                return false;
        }

        return true;
    }

    public double getRotation() {
        return rotation;
    }

    public static boolean collidesWith(@NotNull List<Vector2D> points, @NotNull Circle circle) {
        Vector2D circlePosition = circle.getPosition();
        double radiusSquared = MathUtil.square(circle.getRadius());

        for (Vector2D point : points)
            if (point.distanceSquared(circlePosition) <= radiusSquared)
                return true;

        for (int i = 0; i < points.size(); i++) {
            Vector2D currentPoint = points.get(i);
            Vector2D nextPoint = points.get((i + 1) % points.size());

            Vector2D closest = Vector2D.getClosestPointOnLine(currentPoint, nextPoint, circlePosition);
            if (closest.distanceSquared(circlePosition) <= radiusSquared)
                return true;
        }

        return isPointInsidePolygon(points, circlePosition);
    }

    static boolean isPointInsidePolygon(@NotNull List<Vector2D> points, @NotNull Vector2D point) {
        boolean inside = false;
        int pointsSize = points.size();

        for (int i = 0; i < pointsSize; i++) {
            Vector2D currentPoint = points.get(i);
            Vector2D nextPoint = points.get((i + 1) % pointsSize);

            if (currentPoint.y() > point.y() == nextPoint.y() > point.y())
                continue;

            double xDifference = nextPoint.x() - currentPoint.x();
            double yDifference = nextPoint.y() - currentPoint.y();
            double pointYDifference = point.y() - currentPoint.y();

            if (point.x() < xDifference * pointYDifference / yDifference + currentPoint.x())
                inside = !inside;
        }

        return inside;
    }
}
