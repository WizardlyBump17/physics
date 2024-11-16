package com.wizardlybump17.physics.two.intersection.circle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.util.MathUtil;
import lombok.Data;
import lombok.NonNull;

/**
 * <p>
 * The {@link Circle} to {@link Circle} intersection.
 * </p>
 */
@Data
public class CircleToCircleIntersection implements Intersection {

    private final @NonNull Circle staticShape;
    private final @NonNull Circle movingShape;
    private Vector2D safePosition;

    @Override
    public boolean intersects() {
        return true;
    }

    @Override
    public @NonNull Vector2D getSafePosition() {
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
}
