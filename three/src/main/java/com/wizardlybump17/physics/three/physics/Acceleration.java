package com.wizardlybump17.physics.three.physics;

import com.wizardlybump17.physics.Id;
import com.wizardlybump17.physics.three.Vector3D;
import org.jetbrains.annotations.NotNull;

public record Acceleration(@NotNull Id id, @NotNull Vector3D acceleration) {

    public @NotNull Acceleration with(@NotNull Vector3D acceleration) {
        return new Acceleration(id, acceleration);
    }

    public @NotNull Acceleration add(@NotNull Vector3D value) {
        return new Acceleration(id, this.acceleration.add(value));
    }

    public @NotNull Acceleration subtract(@NotNull Vector3D value) {
        return new Acceleration(id, this.acceleration.subtract(value));
    }

    public @NotNull Acceleration multiply(@NotNull Vector3D value) {
        return new Acceleration(id, this.acceleration.multiply(value));
    }

    public @NotNull Acceleration divide(@NotNull Vector3D value) {
        return new Acceleration(id, this.acceleration.divide(value));
    }
}
