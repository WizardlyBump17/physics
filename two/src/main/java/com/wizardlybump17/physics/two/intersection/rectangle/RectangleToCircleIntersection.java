package com.wizardlybump17.physics.two.intersection.rectangle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import com.wizardlybump17.physics.two.shape.Shape;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Data
public class RectangleToCircleIntersection implements Intersection {

    public static Map<Vector2D, Color> render = new HashMap<>();

    private final @NonNull Circle staticShape;
    private final @NonNull Rectangle movingShape;
    @Setter(AccessLevel.NONE)
    private Vector2D safePosition;

    @Override
    public boolean intersects() {
        return true;
    }

    @Override
    public @NonNull Vector2D getSafePosition() {
        if (safePosition != null)
            return safePosition;

        safePosition = calculateSafePosition();
        return safePosition;
    }

    protected @NotNull Vector2D calculateSafePosition() {
        Vector2D staticPosition = staticShape.getPosition();
        Vector2D movingPosition = movingShape.getPosition();

        double radius = staticShape.getRadius();
        double angle = staticPosition.angleTo(movingPosition);

        Vector2D add = staticPosition.add(
                Math.cos(angle) * (radius + movingShape.getWidth()),
                Math.sin(angle) * (radius + movingShape.getHeight())
        );
        Shape newMovingShape = movingShape.at(add);
        Vector2D closestPoint = newMovingShape.getClosestPoint(staticShape);
        Vector2D edge = staticPosition.add(Math.cos(angle) * (radius), Math.sin(angle) * (radius));

        return add.subtract(closestPoint.subtract(edge));
    }
}
