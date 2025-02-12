package com.wizardlybump17.physics.three;

import org.jetbrains.annotations.NotNull;

public record Line(@NotNull Vector3D start, @NotNull Vector3D end) {

    public Line {
        Vector3D startCopy = start;
        Vector3D endCopy = end;
        start = Vector3D.min(startCopy, endCopy);
        end = Vector3D.max(startCopy, endCopy);
    }
}
