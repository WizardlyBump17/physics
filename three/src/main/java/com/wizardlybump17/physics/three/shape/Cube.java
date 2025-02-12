package com.wizardlybump17.physics.three.shape;

import com.wizardlybump17.physics.three.Vector3D;
import org.jetbrains.annotations.NotNull;

public class Cube extends Shape {

    private final @NotNull Vector3D min;
    private final @NotNull Vector3D max;

    public Cube(@NotNull Vector3D min, @NotNull Vector3D max) {
        this.min = Vector3D.min(min, max);
        this.max = Vector3D.max(min, max);
    }

    @Override
    public @NotNull Vector3D getPosition() {
        return min.midpoint(max);
    }

    @Override
    public double getVolume() {
        return getXSize() * getYSize() * getZSize();
    }

    @Override
    public boolean intersects(@NotNull Shape other) {
        return false;
    }

    @Override
    public boolean hasPoint(@NotNull Vector3D point) {
        return point.isInAABB(min, max);
    }

    @Override
    public @NotNull Cube at(@NotNull Vector3D newPosition) {
        double xSize = getXSize() / 2;
        double ySize = getYSize() / 2;
        double zSize = getZSize() / 2;
        return new Cube(
                newPosition.subtract(xSize, ySize, zSize),
                newPosition.add(xSize, ySize, zSize)
        );
    }

    public double getXSize() {
        return max.x() - min.x();
    }

    public double getYSize() {
        return max.y() - min.y();
    }

    public double getZSize() {
        return max.z() - min.z();
    }
}
