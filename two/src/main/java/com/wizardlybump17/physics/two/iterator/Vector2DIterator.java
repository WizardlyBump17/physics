package com.wizardlybump17.physics.two.iterator;

import com.wizardlybump17.physics.two.position.Vector2D;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Vector2DIterator implements Iterator<Vector2D> {

    private @NotNull Vector2D current;
    private double iterations;
    private final double distance;
    private final double divisor;
    private final @NotNull Vector2D toAdd;

    public Vector2DIterator(@NotNull Vector2D start, @NotNull Vector2D target, double divisor) {
        this.divisor = divisor;
        distance = start.distance(target);
        toAdd = calculateToAdd(start, target);
        current = start.subtract(toAdd);
    }

    protected @NotNull Vector2D calculateToAdd(@NotNull Vector2D start, @NotNull Vector2D target) {
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
        current = current.add(toAdd);
        return current;
    }

    public @NotNull Vector2D getCurrent() {
        return current;
    }

    public double getIterations() {
        return iterations;
    }

    public double getDistance() {
        return distance;
    }

    public double getDivisor() {
        return divisor;
    }

    public @NotNull Vector2D getToAdd() {
        return toAdd;
    }

    @Override
    public String toString() {
        return "Vector2DIterator{" +
                "current=" + current +
                ", iterations=" + iterations +
                ", distance=" + distance +
                ", divisor=" + divisor +
                ", toAdd=" + toAdd +
                '}';
    }
}
