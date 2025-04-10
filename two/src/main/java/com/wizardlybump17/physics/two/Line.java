package com.wizardlybump17.physics.two;

import com.wizardlybump17.physics.two.position.Vector2D;
import org.jetbrains.annotations.NotNull;

public record Line(@NotNull Vector2D start, @NotNull Vector2D end) {

    public @NotNull Vector2D closestPoint(@NotNull Vector2D origin) {
        return Vector2D.getClosestPointOnLine(start, end, origin);
    }

    public @NotNull Vector2D toVector() {
        return end.subtract(start);
    }
}
