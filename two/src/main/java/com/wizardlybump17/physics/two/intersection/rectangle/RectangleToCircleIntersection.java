package com.wizardlybump17.physics.two.intersection.rectangle;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import com.wizardlybump17.physics.two.util.MathUtil;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

@Data
public class RectangleToCircleIntersection implements Intersection {

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
        double movingWidth = movingShape.getWidth();
        double movingHeight = movingShape.getHeight();

        double radius = staticShape.getRadius();
        double angle = staticPosition.angleTo(movingPosition);

        double xChange;
        double yChange;

        if (movingPosition.x() >= staticPosition.x())
            xChange = movingWidth / 2 + MathUtil.EPSILON;
        else
            xChange = -movingWidth / 2 - MathUtil.EPSILON;
        if (movingPosition.y() >= staticPosition.y())
            yChange = movingHeight / 2 + MathUtil.EPSILON;
        else
            yChange = -movingHeight / 2 - MathUtil.EPSILON;

        return staticPosition.add(Math.cos(angle) * radius + xChange, Math.sin(angle) * radius + yChange);
    }
}
