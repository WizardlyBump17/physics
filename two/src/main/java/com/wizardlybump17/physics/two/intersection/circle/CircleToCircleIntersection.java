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

        Vector2D firstPosition = staticShape.getPosition();
        Vector2D secondPosition = movingShape.getPosition();

        double angle = firstPosition.angleTo(secondPosition);
        double distance = firstPosition.distance(secondPosition);
        double toMove = staticShape.getRadius() + movingShape.getRadius() - distance;

        Vector2D safePosition = secondPosition.add(Math.cos(angle) * (toMove + MathUtil.EPSILON), Math.sin(angle) * (toMove + MathUtil.EPSILON));
        this.safePosition = safePosition;
        return safePosition;
    }
}
