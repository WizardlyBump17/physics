package com.wizardlybump17.physics.three.shape;

import com.wizardlybump17.physics.three.Rotatable;
import com.wizardlybump17.physics.three.Vector3D;
import org.jetbrains.annotations.NotNull;

public abstract class Shape implements Cloneable {

    public abstract @NotNull Vector3D getPosition();

    public abstract double getVolume();

    public abstract boolean intersects(@NotNull Shape other);

    public abstract boolean hasPoint(@NotNull Vector3D point);

    public abstract @NotNull Shape at(@NotNull Vector3D newPosition);

    public abstract @NotNull Shape clone();

    /**
     * <p>
     *     Creates a copy of this shape and moves it (set the current position + the given movement).
     *     If the shape is {@link Rotatable}, then the {@link Rotatable#getPivot()} will be moved too.
     * </p>
     * @param movement the movement to apply to the current position
     * @return a new shape with the new position applied
     */
    public @NotNull Shape move(@NotNull Vector3D movement) {
        Shape newShape = at(getPosition().add(movement));
        if (newShape instanceof Rotatable rotatable)
            return (Shape) rotatable.addPivot(movement);
        return newShape;
    }
}
