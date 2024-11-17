package com.wizardlybump17.physics.two.intersection;

import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.EmptyShape;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

public interface Intersection {

    @NotNull Empty EMPTY = new Empty();

    boolean intersects();

    @NotNull Vector2D getSafePosition();

    @NotNull Shape getStaticShape();

    @NotNull Shape getMovingShape();

    final class Empty implements Intersection {

        private Empty() {
        }

        @Override
        public boolean intersects() {
            return false;
        }

        @Override
        public @NotNull Vector2D getSafePosition() {
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
