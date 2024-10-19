package com.wizardlybump17.physics.two.intersection.rectangle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import lombok.Data;
import lombok.NonNull;

@Data
public class RectangleToCircleIntersection implements Intersection {

    private final @NonNull Circle staticShape;
    private final @NonNull Rectangle movingShape;

    @Override
    public boolean intersects() {
        return true;
    }

    @Override
    public @NonNull Vector2D getSafePosition() {
        return movingShape.getPosition();
    }
}
