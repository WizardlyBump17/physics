package com.wizardlybump17.physics.two.intersection.rectangle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Rectangle;
import lombok.Data;
import lombok.NonNull;

@Data
public class RectangleToRectangleIntersection implements Intersection {

    private final @NonNull Rectangle staticShape;
    private final @NonNull Rectangle movingShape;
    private Vector2D safePosition;

    @Override
    public boolean intersects() {
        return true;
    }

    @Override
    public @NonNull Vector2D getSafePosition() {
        if (safePosition != null)
            return safePosition;

        Vector2D firstPosition = staticShape.getPosition();
        Vector2D secondPosition = movingShape.getPosition();
        Vector2D firstMin = staticShape.getMin();
        Vector2D firstMax = staticShape.getMax();

        double secondWidth = movingShape.getWidth() / 2;
        double secondHeight = movingShape.getHeight() / 2;

        double x;
        double y;
        double xRatio;
        double yRatio;

        double firstX = firstPosition.x();
        double firstY = firstPosition.y();
        double secondX = secondPosition.x();
        double secondY = secondPosition.y();

        if (secondX > firstX) { // right
            x = firstMax.x() + secondWidth;
            xRatio = secondX / firstX;
        } else { // left
            x = firstMin.x() - secondWidth;
            xRatio = firstX / secondX;
        }

        if (secondY > firstY) { // up
            y = firstMax.y() + secondHeight;
            yRatio = secondY / firstY;
        } else { // down
            y = firstMin.y() - secondHeight;
            yRatio = firstY / secondY;
        }

        return safePosition = new Vector2D(xRatio >= yRatio ? x : secondX, yRatio > xRatio ? y : secondY);
    }
}
