package com.wizardlybump17.physics.two.util;

import com.wizardlybump17.physics.two.Line;
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

    public static boolean overlapsPolygonToCircle(@NotNull List<Vector2D> points, @NotNull Vector2D circleCenter, double circleRadius) {
        double radiusSquared = MathUtil.square(circleRadius);

        for (Vector2D point : points)
            if (point.distanceSquared(circleCenter) <= radiusSquared)
                return true;

        for (int i = 0; i < points.size(); i++) {
            Vector2D currentPoint = points.get(i);
            Vector2D nextPoint = points.get((i + 1) % points.size());

            Vector2D closest = Vector2D.getClosestPointOnLine(currentPoint, nextPoint, circleCenter);
            if (closest.distanceSquared(circleCenter) <= radiusSquared)
                return true;
        }

        return isPointInsidePolygon(points, circleCenter.x(), circleCenter.y());
    }

    public static boolean overlapsPolygonToCircle(@NotNull RotatingPolygon polygon, @NotNull Circle circle) {
        return overlapsPolygonToCircle(polygon.getTransformedPoints(), circle.getPosition(), circle.getRadius());
    }

    public static boolean isPointInsidePolygon(@NotNull List<Vector2D> points, @NotNull Vector2D point) {
        return isPointInsidePolygon(points, point.x(), point.y());
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

    public static boolean overlapsPolygonToPolygon(@NotNull List<Vector2D> points1, @NotNull List<Vector2D> points2) {
        return overlapsPolygonToPolygon0(points1, points2) && overlapsPolygonToPolygon0(points2, points1);
    }

    private static boolean overlapsPolygonToPolygon0(@NotNull List<Vector2D> points1, @NotNull List<Vector2D> points2) {
        for (int i = 0; i < points1.size(); i++) {
            Vector2D current1 = points1.get(i);
            Vector2D next1 = points1.get((i + 1) % points1.size());

            for (int j = 0; j < points2.size(); j++) {
                Vector2D current2 = points2.get(j);
                Vector2D next2 = points2.get((j + 1) % points2.size());

                if (overlapsLineToLine(current1, next1, current2, next2))
                    return true;
            }
        }

        return isPointInsidePolygon(points1, points2.getFirst()) || isPointInsidePolygon(points2, points1.getFirst());
    }

    public static boolean overlapsLineToLine(@NotNull Vector2D start1, @NotNull Vector2D end1, @NotNull Vector2D start2, @NotNull Vector2D end2) {
        double d1 = start1.direction(start2, end2);
        double d2 = end1.direction(start2, end2);
        double d3 = start2.direction(start1, end1);
        double d4 = end2.direction(start1, end1);

        return ((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) && ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0));
    }

    public static boolean overlapsLineToLine(@NotNull Line line1, @NotNull Line line2) {
        return overlapsLineToLine(line1.start(), line1.end(), line2.start(), line2.end());
    }

    public static boolean overlapsPolygonToRectangle(@NotNull RotatingPolygon polygon, @NotNull Rectangle rectangle) {
        double rectangleHeight = rectangle.getHeight();
        Vector2D rectangleMin = rectangle.getMin();
        Vector2D rectangleMax = rectangle.getMax();

        List<Vector2D> rectanglePoints = RotatingPolygon.sortPoints(List.of(
                rectangleMin,
                rectangleMin.add(0, rectangleHeight),
                rectangleMax,
                rectangleMax.subtract(0, rectangleHeight)
        ));
        return overlapsPolygonToPolygon(rectanglePoints, polygon.getTransformedPoints());
    }

    public static boolean overlapsPolygonToPolygon(@NotNull RotatingPolygon polygon1, @NotNull RotatingPolygon polygon2) {
        return overlapsPolygonToPolygon(polygon1.getTransformedPoints(), polygon2.getTransformedPoints());
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
