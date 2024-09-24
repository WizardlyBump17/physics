package com.wizardlybump17.physics.two.intersection;

import lombok.NonNull;

public interface Intersection {

    @NonNull Empty EMPTY = new Empty();

    boolean intersects();

    class Empty implements Intersection {

        @Override
        public boolean intersects() {
            return false;
        }
    }
}
