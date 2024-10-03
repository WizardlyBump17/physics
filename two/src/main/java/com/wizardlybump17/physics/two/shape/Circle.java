package com.wizardlybump17.physics.two.shape;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.util.MathUtil;
import lombok.*;

@Getter
@EqualsAndHashCode(callSuper = true)
@ToString
@RequiredArgsConstructor
public class Circle extends Shape {

    private final @NonNull Vector2D position;
    private final double radius;

    @Override
    public double getArea() {
        return Math.PI * MathUtil.square(radius);
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public boolean intersects(@NonNull Shape other) {
        return switch (other) {
            case Circle otherCircle -> position.distanceSquared(otherCircle.position) < MathUtil.square(radius + otherCircle.radius);
            default -> false;
        };
    }

    @Override
    public boolean hasPoint(@NonNull Vector2D point) {
        return point.distanceSquared(position) < MathUtil.square(radius);
    }

    @Override
    public @NonNull Shape at(@NonNull Vector2D position) {
        return new Circle(position, radius);
    }

    @Override
    public @NonNull Intersection intersect(@NonNull Shape other) {
        return Intersection.EMPTY;
    }
}
