package com.wizardlybump17.physics.two.iterator;

import com.wizardlybump17.physics.two.position.Vector2D;
import lombok.NonNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Vector2DIterator implements Iterator<Vector2D> {

    private @NonNull Vector2D current;
    private double iterations;
    private final double distance;
    private final double divisor;
    private final @NonNull Vector2D toAdd;

    public Vector2DIterator(@NonNull Vector2D start, @NonNull Vector2D target, double divisor) {
        this.divisor = divisor;
        distance = start.distance(target);
        toAdd = calculateToAdd(start, target);
        current = start.subtract(toAdd);
    }

    protected @NonNull Vector2D calculateToAdd(@NonNull Vector2D start, @NonNull Vector2D target) {
        double x = target.x() - start.x();
        double y = target.y() - start.y();
        return new Vector2D(x / divisor, y / divisor);
    }

    @Override
    public boolean hasNext() {
        return iterations < distance;
    }

    @Override
    public Vector2D next() {
        if (!hasNext())
            throw new NoSuchElementException();
        iterations += distance / divisor;
        return current = current.add(toAdd);
    }
}
