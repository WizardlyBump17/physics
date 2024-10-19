package com.wizardlybump17.physics.two.intersection.rectangle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import com.wizardlybump17.physics.two.util.MathUtil;
import lombok.Data;
import lombok.NonNull;

@Data
public class RectangleToCircleIntersection implements Intersection {

    private final @NonNull Circle circle;
    private final @NonNull Rectangle rectangle;

    @Override
    public boolean intersects() {
        return true;
    }

    @Override
    public @NonNull Vector2D getSafePosition() {
        Vector2D circlePosition = circle.getPosition();
        Vector2D rectanglePosition = rectangle.getPosition();
        Vector2D rectangleMin = rectangle.getMin();
        Vector2D rectangleMax = rectangle.getMax();

        double circleX = circlePosition.x();
        double circleY = circlePosition.y();

        double clampedX = Math.clamp(circleX, rectangleMin.x(), rectangleMax.x());
        double clampedY = Math.clamp(circleY, rectangleMin.y(), rectangleMax.y());

        double closestX;
        double closestY;

        if (rectangle.hasPoint(circlePosition)) {
            double xRatio;
            double yRatio;

            double realClosestX;
            double realClosestY;

            if (circleX >= rectanglePosition.x()) {
                realClosestX = rectangleMax.x() + circle.getRadius() * 2 + MathUtil.EPSILON;
                xRatio = circleX / rectanglePosition.x();
            } else {
                realClosestX = rectangleMin.x() - circle.getRadius() * 2 - MathUtil.EPSILON;
                xRatio = rectanglePosition.x() / circleX;
            }

            if (circleY >= rectanglePosition.y()) {
                realClosestY = rectangleMax.y() + circle.getRadius() * 2 + MathUtil.EPSILON;
                yRatio = circleY / rectanglePosition.y();
            } else {
                realClosestY = rectangleMin.y() - circle.getRadius() * 2 - MathUtil.EPSILON;
                yRatio = rectanglePosition.y() / circleY;
            }

            if (xRatio >= yRatio)
                closestX = realClosestX;
            else
                closestX = clampedX;

            if (yRatio > xRatio)
                closestY = realClosestY;
            else
                closestY = clampedY;
        } else {
            if (circleX >= rectanglePosition.x())
                closestX = clampedX + MathUtil.EPSILON;
            else
                closestX = clampedX - MathUtil.EPSILON;
            if (circleY >= rectanglePosition.y())
                closestY = clampedY + MathUtil.EPSILON;
            else
                closestY = clampedY - MathUtil.EPSILON;
        }

        Vector2D closest = new Vector2D(closestX, closestY);
        return closest.add(circlePosition.subtract(closest).normalize().multiply(circle.getRadius()));
    }
}
