package com.wizardlybump17.physics.two.intersection.circle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import com.wizardlybump17.physics.util.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class CircleToRectangleIntersection implements Intersection {

    private final @NotNull Circle movingShape;
    private final @NotNull Rectangle staticShape;
    private Vector2D safePosition;

    public CircleToRectangleIntersection(@NotNull Circle movingShape, @NotNull Rectangle staticShape) {
        this.movingShape = movingShape;
        this.staticShape = staticShape;
    }

    @Override
    public @NotNull Circle getMovingShape() {
        return movingShape;
    }

    @Override
    public @NotNull Rectangle getStaticShape() {
        return staticShape;
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
        Vector2D circlePosition = movingShape.getPosition();
        double radius = movingShape.getRadius();
        Vector2D closestPoint = staticShape.getClosestPoint(circlePosition);

        double angle;
        if (staticShape.hasPoint(circlePosition))
            angle = circlePosition.angleTo(closestPoint);
        else
            angle = closestPoint.angleTo(circlePosition);

        return closestPoint.add(
                Math.cos(angle) * (radius + MathUtil.EPSILON),
                Math.sin(angle) * (radius + MathUtil.EPSILON)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        CircleToRectangleIntersection that = (CircleToRectangleIntersection) o;
        return Objects.equals(movingShape, that.movingShape) && Objects.equals(staticShape, that.staticShape) && Objects.equals(safePosition, that.safePosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movingShape, staticShape, safePosition);
    }

    @Override
    public String toString() {
        return "CircleToRectangleIntersection{" +
                "movingShape=" + movingShape +
                ", staticShape=" + staticShape +
                ", safePosition=" + safePosition +
                '}';
    }
}
