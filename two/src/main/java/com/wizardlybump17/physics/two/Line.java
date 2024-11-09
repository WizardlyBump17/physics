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
        Vector2D vector = toVector();
        return start.add(vector.multiply(Math.clamp(origin.subtract(start).dot(vector) / vector.dot(vector), 0, 1)));
    }

    public @NotNull Vector2D toVector() {
        return end.subtract(start);
    }
}
