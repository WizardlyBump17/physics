package com.wizardlybump17.physics.two.intersection;

import com.wizardlybump17.physics.two.position.Vector2D;
import lombok.NonNull;

public interface Intersection {

    @NonNull Empty EMPTY = new Empty();

    boolean intersects();

    @NonNull Vector2D getSafePosition();

    class Empty implements Intersection {

        @Override
        public boolean intersects() {
            return false;
        }

        @Override
        public @NonNull Vector2D getSafePosition() {
            return Vector2D.ZERO;
        }
    }
}
