package com.wizardlybump17.physics.two.intersection.rectangle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
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

        double clampedX = Math.clamp(circlePosition.x(), rectangleMin.x(), rectangleMax.x());
        double clampedY = Math.clamp(circlePosition.y(), rectangleMin.y(), rectangleMax.y());
        double realClosestX;
        double realClosestY;
        double xRatio = 0;
        double yRatio;

        if (rectangle.hasPoint(circlePosition)) {
            xRatio = circlePosition.x() / rectanglePosition.x();
            yRatio = circlePosition.y() / rectanglePosition.y();

            realClosestX = xRatio >= yRatio ? (circlePosition.x() > rectanglePosition.x() ? rectangleMax.x() : rectangleMin.x()) : clampedX;
            realClosestY = yRatio > xRatio ? (circlePosition.y() > rectanglePosition.y() ? rectangleMax.y() : rectangleMin.y()) : clampedY;
        } else {
            realClosestX = Math.clamp(circlePosition.x(), rectangleMin.x(), rectangleMax.x());
            realClosestY = Math.clamp(circlePosition.y(), rectangleMin.y(), rectangleMax.y());
        }

        Vector2D closestPoint = new Vector2D(realClosestX, realClosestY);
        Vector2D subtract = circlePosition.subtract(closestPoint);
        Vector2D direction = subtract.normalize();

        double multiplier = circle.getRadius() + 0.00000000001;
        return closestPoint.add(direction.multiply(xRatio != 0 ? -multiplier : multiplier));
    }
}
