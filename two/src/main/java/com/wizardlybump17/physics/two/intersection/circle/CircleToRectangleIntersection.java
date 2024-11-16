package com.wizardlybump17.physics.two.intersection.circle;

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
public class CircleToRectangleIntersection implements Intersection {

    private final @NonNull Circle movingShape;
    private final @NonNull Rectangle staticShape;
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
        Vector2D circlePosition = movingShape.getPosition();
        double radius = movingShape.getRadius();
        Vector2D closestPoint = staticShape.getClosestPoint(circlePosition);

        double angle;
        if (staticShape.hasPoint(circlePosition))
            angle = circlePosition.angleTo(closestPoint);
        else
            angle = closestPoint.angleTo(circlePosition);

        return closestPoint.add(
                Math.cos(angle) * (radius + MathUtil.EPSILON),
                Math.sin(angle) * (radius + MathUtil.EPSILON)
        );
    }
}
