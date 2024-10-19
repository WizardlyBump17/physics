package com.wizardlybump17.physics.two.intersection;

import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.EmptyShape;
import com.wizardlybump17.physics.two.shape.Shape;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

public interface Intersection {

    @NonNull Empty EMPTY = new Empty();

    boolean intersects();

    @NonNull Vector2D getSafePosition();

    @NotNull Shape getStaticShape();

    @NotNull Shape getMovingShape();

    class Empty implements Intersection {

        @Override
        public boolean intersects() {
            return false;
        }

        @Override
        public @NonNull Vector2D getSafePosition() {
            return Vector2D.ZERO;
        }

        @Override
        public @NotNull Shape getStaticShape() {
            return EmptyShape.INSTANCE;
        }

        @Override
        public @NotNull Shape getMovingShape() {
            return EmptyShape.INSTANCE;
        }
    }
}
