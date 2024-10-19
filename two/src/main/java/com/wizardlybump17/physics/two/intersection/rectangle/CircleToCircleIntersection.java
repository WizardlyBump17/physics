package com.wizardlybump17.physics.two.intersection.rectangle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.util.MathUtil;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

/**
 * <p>
 * The {@link Circle} to {@link Circle} intersection.
 * </p>
 */
@Data
public class CircleToCircleIntersection implements Intersection {

    private final @NonNull Circle first;
    private final @NonNull Circle second;
    private Vector2D safePosition;

    @Override
    public boolean intersects() {
        return true;
    }

    @Override
    public @NonNull Vector2D getSafePosition() {
        if (safePosition != null)
            return safePosition;

        Vector2D firstPosition = first.getPosition();
        Vector2D secondPosition = second.getPosition();

        double angle = Math.atan2(secondPosition.y() - firstPosition.y(), secondPosition.x() - firstPosition.x());
        double distance = firstPosition.distance(secondPosition);
        double toMove = first.getRadius() + second.getRadius() - distance;

        Vector2D safePosition = secondPosition.add(Math.cos(angle) * (toMove + MathUtil.EPSILON), Math.sin(angle) * (toMove + MathUtil.EPSILON));
        this.safePosition = safePosition;
        return safePosition;
    }

    /**
     * <p>
     * Returns the first {@link Circle} of the intersection.
     * This is the static circle when you are dragging another one, for example.
     * </p>
     *
     * @return the first {@link Circle} of the intersection
     */
    public @NotNull Circle getFirst() {
        return first;
    }

    /**
     * <p>
     * Returns the second {@link Circle} of the intersection.
     * This is the circle that is being dragged, for example.
     * </p>
     *
     * @return the second {@link Circle} of the intersection
     */
    public @NotNull Circle getSecond() {
        return second;
    }
}
