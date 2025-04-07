package com.wizardlybump17.physics.three.shape.rotating;

import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class RotatingCube extends Shape {

    private final @NotNull Vector3D position;
    private final @NotNull List<Vector3D> points;
    private final @NotNull List<Vector3D> transformedPoints;
    private final @NotNull Vector3D rotation;

    public RotatingCube(@NotNull Vector3D position, @NotNull List<Vector3D> points, @NotNull Vector3D rotation) {
        this.position = position;
        this.points = Collections.unmodifiableList(points);
        this.rotation = rotation;
        transformedPoints = points.stream()
                .map(point -> position.add(point.rotateAround(rotation)))
                .toList();
    }

    @Override
    public @NotNull Vector3D getPosition() {
        return position;
    }

    @Override
    public double getVolume() {
        return 0;
    }

    @Override
    public boolean intersects(@NotNull Shape other) {
        return false;
    }

    @Override
    public boolean hasPoint(@NotNull Vector3D point) {
        return false;
    }

    @Override
    public @NotNull RotatingCube at(@NotNull Vector3D newPosition) {
        return new RotatingCube(position, points, rotation);
    }

    public @NotNull List<Vector3D> getPoints() {
        return points;
    }

    public @NotNull List<Vector3D> getTransformedPoints() {
        return transformedPoints;
    }

    public @NotNull Vector3D getRotation() {
        return rotation;
    }

    public @NotNull RotatingCube withRotation(@NotNull Vector3D rotation) {
        return new RotatingCube(position, points, rotation);
    }

    public @NotNull RotatingCube addRotation(@NotNull Vector3D rotation) {
        return new RotatingCube(position, points, this.rotation.add(rotation));
    }

    public @NotNull RotatingCube subtractRotation(@NotNull Vector3D rotation) {
        return new RotatingCube(position, points, this.rotation.subtract(rotation));
    }

    public @NotNull RotatingCube withPoints(@NotNull List<Vector3D> points) {
        return new RotatingCube(position, points, rotation);
    }
}
