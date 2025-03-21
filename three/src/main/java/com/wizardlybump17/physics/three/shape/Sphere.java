package com.wizardlybump17.physics.three.shape;

import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.util.MathUtil;
import org.jetbrains.annotations.NotNull;

public class Sphere extends Shape {

    private final @NotNull Vector3D position;
    private final double radius;

    public Sphere(@NotNull Vector3D position, double radius) {
        this.position = position;
        this.radius = radius;
    }

    @Override
    public @NotNull Vector3D getPosition() {
        return position;
    }

    @Override
    public double getVolume() {
        return 4.0 / 3.0 * Math.PI * MathUtil.cube(radius);
    }

    @Override
    public boolean intersects(@NotNull Shape other) {
        return switch (other) {
            case Sphere otherSphere -> position.distanceSquared(otherSphere.position) <= MathUtil.square(radius + otherSphere.radius);
            case Cube cube -> {
                Vector3D cubePosition = cube.getPosition();
                Vector3D position = getPosition();

                double xDistance = Math.abs(position.x() - cubePosition.x());
                double yDistance = Math.abs(position.y() - cubePosition.y());
                double zDistance = Math.abs(position.z() - cubePosition.z());

                double xSize = cube.getXSize() / 2;
                double ySize = cube.getYSize() / 2;
                double zSize = cube.getZSize() / 2;

                if (xDistance > xSize + radius || yDistance > ySize + radius || zDistance > zSize + radius)
                    yield false;

                if (xDistance <= xSize || yDistance <= ySize || zDistance <= zSize)
                    yield true;

                yield MathUtil.square(xDistance - xSize) + MathUtil.square(yDistance - ySize) + MathUtil.square(zDistance - zSize) <= MathUtil.square(radius);
            }
            default -> false;
        };
    }

    @Override
    public boolean hasPoint(@NotNull Vector3D point) {
        return point.distanceSquared(position) <= MathUtil.square(radius);
    }

    @Override
    public @NotNull Sphere at(@NotNull Vector3D newPosition) {
        return new Sphere(newPosition, radius);
    }

    public double getRadius() {
        return radius;
    }

    public @NotNull Sphere withRadius(double radius) {
        return new Sphere(position, radius);
    }
}
