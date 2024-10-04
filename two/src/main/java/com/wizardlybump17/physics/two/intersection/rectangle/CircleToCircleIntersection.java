package com.wizardlybump17.physics.two.intersection.rectangle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import lombok.Data;
import lombok.NonNull;

@Data
public class CircleToCircleIntersection implements Intersection {

    public static final double ADD = 0.000000000001;

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

        return safePosition = secondPosition.add(Math.cos(angle) * (toMove + ADD), Math.sin(angle) * (toMove + ADD));
    }
}
