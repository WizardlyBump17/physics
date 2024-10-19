package com.wizardlybump17.physics.two.intersection.rectangle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import com.wizardlybump17.physics.two.util.MathUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Data
public class RectangleToCircleIntersection implements Intersection {

    private final @NonNull Circle circle;
    private final @NonNull Rectangle rectangle;
    @Setter(AccessLevel.NONE)
    private Vector2D safePosition;

    @Override
    public boolean intersects() {
        return true;
    }

    @Override
    public @NonNull Vector2D getSafePosition() {
        if (safePosition != null)
            return safePosition;

        safePosition = calculateSafePosition();
        return safePosition;
    }

    protected @NotNull Vector2D calculateSafePosition() {
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

        if (rectangle.hasPoint(circlePosition)) { //this check is necessary because the circle may get inside the rectangle
            double xRatio;
            double yRatio;

            double realClosestX;
            double realClosestY;

            if (circleX >= rectanglePosition.x()) { //right
                realClosestX = rectangleMax.x() + circle.getRadius() * 2 + MathUtil.EPSILON;
                xRatio = circleX / rectanglePosition.x();
            } else { //left
                realClosestX = rectangleMin.x() - circle.getRadius() * 2 - MathUtil.EPSILON;
                xRatio = rectanglePosition.x() / circleX;
            }

            if (circleY >= rectanglePosition.y()) { //up
                realClosestY = rectangleMax.y() + circle.getRadius() * 2 + MathUtil.EPSILON;
                yRatio = circleY / rectanglePosition.y();
            } else { //down
                realClosestY = rectangleMin.y() - circle.getRadius() * 2 - MathUtil.EPSILON;
                yRatio = rectanglePosition.y() / circleY;
            }

            if (xRatio >= yRatio) //it is closer to the X axis
                closestX = realClosestX;
            else
                closestX = clampedX;

            if (yRatio > xRatio) //it is closer to the Y axis
                closestY = realClosestY;
            else
                closestY = clampedY;
        } else {
            if (circleX >= rectanglePosition.x()) //right
                closestX = clampedX + MathUtil.EPSILON;
            else //left
                closestX = clampedX - MathUtil.EPSILON;

            if (circleY >= rectanglePosition.y()) //right
                closestY = clampedY + MathUtil.EPSILON;
            else //left
                closestY = clampedY - MathUtil.EPSILON;
        }

        double subtractedX = circleX - closestX;
        double subtractedY = circleY - closestY;

        double length = Math.sqrt(MathUtil.square(subtractedX) + MathUtil.square(subtractedY));

        double normalizedX = subtractedX / length;
        double normalizedY = subtractedY / length;

        return new Vector2D(closestX + normalizedX * circle.getRadius(), closestY + normalizedY * circle.getRadius());
    }
}
