package com.wizardlybump17.physics.three.shape;

import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.util.MathUtil;
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
        return switch (other) {
            case Cube otherCube -> {
                Vector3D otherMin = otherCube.getMin();
                Vector3D otherMax = otherCube.getMax();
                yield min.x() < otherMax.x() && max.x() > otherMin.x()
                        && min.y() < otherMax.y() && max.y() > otherMin.y()
                        && min.z() < otherMax.z() && max.z() > otherMin.z();
            }
            case Sphere sphere -> {
                Vector3D circlePosition = sphere.getPosition();
                Vector3D position = getPosition();

                double xDistance = Math.abs(position.x() - circlePosition.x());
                double yDistance = Math.abs(position.y() - circlePosition.y());
                double zDistance = Math.abs(position.z() - circlePosition.z());

                double radius = sphere.getRadius();

                double xSize = getXSize() / 2;
                double ySize = getYSize() / 2;
                double zSize = getZSize() / 2;

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

    public @NotNull Vector3D getMin() {
        return min;
    }

    public @NotNull Vector3D getMax() {
        return max;
    }
}
