package com.wizardlybump17.physics.two.intersection.rectangle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import com.wizardlybump17.physics.util.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class RectangleToCircleIntersection implements Intersection {

    private final @NotNull Circle staticShape;
    private final @NotNull Rectangle movingShape;
    private Vector2D safePosition;

    public RectangleToCircleIntersection(@NotNull Circle staticShape, @NotNull Rectangle movingShape) {
        this.staticShape = staticShape;
        this.movingShape = movingShape;
    }

    @Override
    public @NotNull Circle getStaticShape() {
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
        Vector2D staticPosition = staticShape.getPosition();
        Vector2D movingPosition = movingShape.getPosition();

        double radius = staticShape.getRadius();
        double angle = staticPosition.angleTo(movingPosition);

        return staticPosition.add(
                Math.cos(angle) * (radius + MathUtil.EPSILON),
                Math.sin(angle) * (radius + MathUtil.EPSILON)
        );
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        RectangleToCircleIntersection that = (RectangleToCircleIntersection) o;
        return Objects.equals(staticShape, that.staticShape) && Objects.equals(movingShape, that.movingShape) && Objects.equals(safePosition, that.safePosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staticShape, movingShape, safePosition);
    }

    @Override
    public String toString() {
        return "RectangleToCircleIntersection{" +
                "staticShape=" + staticShape +
                ", movingShape=" + movingShape +
                ", safePosition=" + safePosition +
                '}';
    }
}
