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

    public static boolean overlapsPolygonToCircle(@NotNull List<Vector2D> points, @NotNull Circle circle) {
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

        return isPointInsidePolygon(points, circlePosition.x(), circlePosition.y());
    }

    public static boolean overlapsPolygonToCircle(@NotNull RotatingPolygon polygon, @NotNull Circle circle) {
        return overlapsPolygonToCircle(polygon.getRotatedPoints(), circle);
    }

    public static boolean isPointInsidePolygon(@NotNull List<Vector2D> points, double x, double y) {
        boolean inside = false;
        int pointsSize = points.size();

        for (int i = 0; i < pointsSize; i++) {
            Vector2D currentPoint = points.get(i);
            Vector2D nextPoint = points.get((i + 1) % pointsSize);

            if (currentPoint.y() > y == nextPoint.y() > y)
                continue;

            double xDifference = nextPoint.x() - currentPoint.x();
            double yDifference = nextPoint.y() - currentPoint.y();
            double pointYDifference = y - currentPoint.y();

            if (x < xDifference * pointYDifference / yDifference + currentPoint.x())
                inside = !inside;
        }

        return inside;
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

    public static boolean overlapsPolygonToRectangle(@NotNull RotatingPolygon polygon, @NotNull Rectangle rectangle) {
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

    public static boolean overlapsPolygonToPolygon(@NotNull RotatingPolygon polygon1, @NotNull RotatingPolygon polygon2) {
        return overlapsPolygonToPolygon(polygon1.getRotatedPoints(), polygon2.getRotatedPoints())
                && overlapsPolygonToPolygon(polygon2.getRotatedPoints(), polygon1.getRotatedPoints());
    }

    public static boolean overlapsRectangleToCircle(@NotNull Vector2D rectangleCenter, double rectangleWidth, double rectangleHeight, @NotNull Vector2D circleCenter, double radius) {
        double xDistance = Math.abs(circleCenter.x() - rectangleCenter.x());
        double yDistance = Math.abs(circleCenter.y() - rectangleCenter.y());

        double halfWidth = rectangleWidth / 2;
        double halfHeight = rectangleHeight/ 2;

        if (xDistance > halfWidth + radius || yDistance > halfHeight + radius)
            return false;

        if (xDistance <= halfWidth || yDistance <= halfHeight)
            return true;

        return MathUtil.square(xDistance - halfWidth) + MathUtil.square(yDistance - halfHeight) <= MathUtil.square(radius);
    }

    public static boolean overlapsRectangleToCircle(@NotNull Rectangle rectangle, @NotNull Circle circle) {
        return overlapsRectangleToCircle(
                rectangle.getPosition(),
                rectangle.getWidth(), rectangle.getHeight(),
                circle.getPosition(), circle.getRadius()
        );
    }

    public static boolean overlapsRectangleToRectangle(@NotNull Vector2D min1, @NotNull Vector2D max1, @NotNull Vector2D min2, @NotNull Vector2D max2) {
        return min1.x() < max2.x() && max1.x() > min2.x()
                && min1.y() < max2.y() && max1.y() > min2.y();
    }

    public static boolean overlapsRectangleToRectangle(@NotNull Rectangle rectangle1, @NotNull Rectangle rectangle2) {
        return overlapsRectangleToRectangle(
                rectangle1.getMin(), rectangle1.getMax(),
                rectangle2.getMin(), rectangle2.getMax()
        );
    }

    public static boolean overlapsCircleToCircle(@NotNull Vector2D center1, double radius1, @NotNull Vector2D center2, double radius2) {
        return center1.distanceSquared(center2) < MathUtil.square(radius1 + radius2);
    }

    public static boolean overlapsCircleToCircle(@NotNull Circle circle1, @NotNull Circle circle2) {
        return overlapsCircleToCircle(
                circle1.getPosition(), circle1.getRadius(),
                circle2.getPosition(), circle2.getRadius()
        );
    }
}
