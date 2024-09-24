package com.wizardlybump17.physics.two.intersection.rectangle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Rectangle;
import lombok.Data;
import lombok.NonNull;

@Data
public class RectangleToRectangleIntersection implements Intersection {

    private final @NonNull Rectangle first;
    private final @NonNull Rectangle second;
    private final @NonNull Rectangle intersection;

    @Override
    public boolean intersects() {
        return true;
    }

    @Override
    public @NonNull Vector2D getSafePosition() {
        return second.getPosition();
    }
}
