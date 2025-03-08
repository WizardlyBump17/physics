package com.wizardlybump17.physics.three.shape;

import com.wizardlybump17.physics.three.Vector3D;
import org.jetbrains.annotations.NotNull;

public abstract class Shape {

    public abstract @NotNull Vector3D getPosition();

    public abstract double getVolume();

    public abstract boolean intersects(@NotNull Shape other);

    public abstract boolean hasPoint(@NotNull Vector3D point);

    public abstract @NotNull Shape at(@NotNull Vector3D newPosition);
}
