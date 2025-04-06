package com.wizardlybump17.physics.two;

import com.wizardlybump17.physics.two.position.Vector2D;
import org.jetbrains.annotations.NotNull;

public record Line(@NotNull Vector2D start, @NotNull Vector2D end) {

    public Line {
        Vector2D startArg = start;
        Vector2D endArg = end;
        start = Vector2D.min(startArg, endArg);
        end = Vector2D.max(startArg, endArg);
    }

    public @NotNull Vector2D closestPoint(@NotNull Vector2D origin) {
        return Vector2D.getClosestPointOnLine(start, end, origin);
    }

    public @NotNull Vector2D toVector() {
        return end.subtract(start);
    }
}
