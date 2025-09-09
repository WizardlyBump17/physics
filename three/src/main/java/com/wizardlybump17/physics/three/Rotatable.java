package com.wizardlybump17.physics.three;

import org.jetbrains.annotations.NotNull;

public interface Rotatable {

    @NotNull Vector3D getRotation();

    @NotNull Rotatable setRotation(@NotNull Vector3D rotation);

    default @NotNull Rotatable addRotation(@NotNull Vector3D rotation) {
        return setRotation(getRotation().add(rotation));
    }

    default @NotNull Rotatable subtractRotation(@NotNull Vector3D rotation) {
        return setRotation(getRotation().subtract(rotation));
    }

    @NotNull Vector3D getPivot();

    @NotNull Rotatable setPivot(@NotNull Vector3D pivot);

    default @NotNull Rotatable addPivot(@NotNull Vector3D pivot) {
        return setPivot(getPivot().add(pivot));
    }

    default @NotNull Rotatable subtractPivot(@NotNull Vector3D pivot) {
        return setPivot(getPivot().subtract(pivot));
    }
}
