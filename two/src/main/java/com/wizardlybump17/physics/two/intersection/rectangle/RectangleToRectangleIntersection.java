package com.wizardlybump17.physics.two.intersection.rectangle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RectangleToRectangleIntersection implements Intersection {

    private final @NotNull Rectangle staticShape;
    private final @NotNull Rectangle movingShape;
    private Vector2D safePosition;

    public RectangleToRectangleIntersection(@NotNull Rectangle staticShape, @NotNull Rectangle movingShape) {
        this.staticShape = staticShape;
        this.movingShape = movingShape;
    }

    @Override
    public @NotNull Rectangle getStaticShape() {
        return staticShape;
    }

    @Override
    public @NotNull Rectangle getMovingShape() {
        return movingShape;
    }

    @Override
    public boolean intersects() {
        return true;
    }

    @Override
    public @NotNull Vector2D getSafePosition() {
        if (safePosition != null)
            return safePosition;

        safePosition = calculateSafePosition();
        return safePosition;
    }

    protected @NotNull Vector2D calculateSafePosition() {
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

        return new Vector2D(xRatio >= yRatio ? x : secondX, yRatio > xRatio ? y : secondY);
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        RectangleToRectangleIntersection that = (RectangleToRectangleIntersection) o;
        return Objects.equals(staticShape, that.staticShape) && Objects.equals(movingShape, that.movingShape) && Objects.equals(safePosition, that.safePosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staticShape, movingShape, safePosition);
    }
}
