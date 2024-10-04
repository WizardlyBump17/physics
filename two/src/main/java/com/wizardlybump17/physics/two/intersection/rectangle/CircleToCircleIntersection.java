package com.wizardlybump17.physics.two.intersection.rectangle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import lombok.Data;
import lombok.NonNull;

@Data
public class CircleToCircleIntersection implements Intersection {

    private final @NonNull Circle first;
    private final @NonNull Circle second;

    @Override
    public boolean intersects() {
        return true;
    }

    @Override
    public @NonNull Vector2D getSafePosition() {
        return second.getPosition();
    }
}
