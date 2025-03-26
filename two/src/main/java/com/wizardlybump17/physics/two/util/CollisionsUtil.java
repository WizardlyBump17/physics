package com.wizardlybump17.physics.two.util;

import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import com.wizardlybump17.physics.two.shape.rotating.RotatingPolygon;
import com.wizardlybump17.physics.util.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class CollisionsUtil {

    private CollisionsUtil() {
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

    public static boolean isPointInsidePolygon(@NotNull List<Vector2D> points, @NotNull Vector2D point) {
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
        return overlapsPolygonToPolygon(rectanglePoints, polygon.getRotatedPoints()) && overlapsPolygonToPolygon(polygon.getRotatedPoints(), rectanglePoints);
    }

    public static boolean overlaps(@NotNull RotatingPolygon polygon1, @NotNull RotatingPolygon polygon2) {
        return overlapsPolygonToPolygon(polygon1.getRotatedPoints(), polygon2.getRotatedPoints())
                && overlapsPolygonToPolygon(polygon2.getRotatedPoints(), polygon1.getRotatedPoints());
    }
}
