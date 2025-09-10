package com.wizardlybump17.physics.three.shape.rotating;

import com.wizardlybump17.physics.three.Rotatable;
import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RotatingCube extends Shape implements Rotatable {

    private final @NotNull Vector3D position;
    private final @NotNull Vector3D transformedPosition;
    private final @NotNull List<Vector3D> points;
    private final @NotNull List<Vector3D> transformedPoints;
    private final @NotNull Vector3D rotation;
    private final @NotNull Vector3D pivot;

    public RotatingCube(@NotNull Vector3D position, @NotNull List<Vector3D> points, @NotNull Vector3D rotation, @NotNull Vector3D pivot) {
        this(position, position.rotateAround(rotation, pivot), points, rotation, pivot, false);
    }

    public RotatingCube(@NotNull Vector3D position, @NotNull List<Vector3D> points, @NotNull Vector3D rotation) {
        this(position, position, points, rotation, position, false);
    }

    private RotatingCube(@NotNull Vector3D position, @NotNull Vector3D transformedPosition, @NotNull List<Vector3D> points, @NotNull Vector3D rotation, @NotNull Vector3D pivot, boolean sorted) {
        this.position = position;
        this.transformedPosition = transformedPosition;
        this.points = Collections.unmodifiableList(sorted ? points : sortPoints(points));
        this.rotation = rotation;
        transformedPoints = points.stream()
                    .map(point -> position.add(point.rotateAround(rotation, pivot)))
                    .toList();
        this.pivot = pivot;
    }

    @Override
    public @NotNull Vector3D getPosition() {
        return position;
    }

    public @NotNull Vector3D getTransformedPosition() {
        return transformedPosition;
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
        return new RotatingCube(newPosition, newPosition.rotateAround(rotation, pivot), points, rotation, pivot, true);
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

    @Override
    public @NotNull RotatingCube setRotation(@NotNull Vector3D rotation) {
        return new RotatingCube(position, position.rotateAround(rotation, pivot), points, rotation, pivot, true);
    }

    @Override
    public @NotNull RotatingCube addRotation(@NotNull Vector3D rotation) {
        Vector3D newRotation = this.rotation.add(rotation);
        return new RotatingCube(position, position.rotateAround(newRotation, pivot), points, newRotation, pivot, true);
    }

    @Override
    public @NotNull RotatingCube subtractRotation(@NotNull Vector3D rotation) {
        Vector3D newRotation = this.rotation.subtract(rotation);
        return new RotatingCube(position, position.rotateAround(newRotation, pivot), points, newRotation, pivot, true);
    }

    public @NotNull RotatingCube withPoints(@NotNull List<Vector3D> points) {
        return new RotatingCube(position, points, rotation, pivot);
    }

    public static @NotNull List<Vector3D> sortPoints(@NotNull List<Vector3D> points) {
        List<Vector3D> result = new ArrayList<>(points);

        Vector3D sum = Vector3D.ZERO;
        for (Vector3D point : points)
            sum = sum.add(point);

        Vector3D center = sum.divide(points.size());

        result.sort(Comparator.comparingDouble(point -> point.subtract(center).lengthSquared()));

        return result;
    }

    @Override
    public @NotNull RotatingCube clone() {
        return new RotatingCube(position, transformedPosition, points, rotation, pivot, true);
    }

    @Override
    public @NotNull Vector3D getPivot() {
        return pivot;
    }

    @Override
    public @NotNull Rotatable setPivot(@NotNull Vector3D pivot) {
        return new RotatingCube(position, position.rotateAround(rotation, pivot), points, rotation, pivot, true);
    }

    @Override
    public @NotNull Rotatable addPivot(@NotNull Vector3D pivot) {
        Vector3D newPivot = this.pivot.add(pivot);
        return new RotatingCube(position, position.rotateAround(rotation, newPivot), points, rotation, newPivot, true);
    }

    @Override
    public @NotNull Rotatable subtractPivot(@NotNull Vector3D pivot) {
        Vector3D newPivot = this.pivot.subtract(pivot);
        return new RotatingCube(position, position.rotateAround(rotation, newPivot), points, rotation, newPivot, true);
    }
}
