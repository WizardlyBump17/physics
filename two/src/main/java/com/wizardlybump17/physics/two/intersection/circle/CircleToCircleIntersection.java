package com.wizardlybump17.physics.two.intersection.circle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.util.MathUtil;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/**
 * <p>
 * The {@link Circle} to {@link Circle} intersection.
 * </p>
 */
public class CircleToCircleIntersection implements Intersection {

    private final @NotNull Circle staticShape;
    private final @NotNull Circle movingShape;
    private Vector2D safePosition;

    public CircleToCircleIntersection(@NotNull Circle staticShape, @NotNull Circle movingShape) {
        this.staticShape = staticShape;
        this.movingShape = movingShape;
    }

    @Override
    public @NotNull Circle getStaticShape() {
        return staticShape;
    }

    @Override
    public @NotNull Circle getMovingShape() {
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

        double angle = staticShape.getPosition().angleTo(movingShape.getPosition());
        double radius = movingShape.getRadius();

        Vector2D position = staticShape.getClosestPoint(movingShape)
                .add(
                        Math.cos(angle) * (radius + MathUtil.EPSILON),
                        Math.sin(angle) * (radius + MathUtil.EPSILON)
                );
        safePosition = position;
        return position;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        CircleToCircleIntersection that = (CircleToCircleIntersection) o;
        return Objects.equals(staticShape, that.staticShape) && Objects.equals(movingShape, that.movingShape) && Objects.equals(safePosition, that.safePosition);
    }

    @Override
    public int hashCode() {
        return Objects.hash(staticShape, movingShape, safePosition);
    }

    @Override
    public String toString() {
        return "CircleToCircleIntersection{" +
                "staticShape=" + staticShape +
                ", movingShape=" + movingShape +
                ", safePosition=" + safePosition +
                '}';
    }
}
