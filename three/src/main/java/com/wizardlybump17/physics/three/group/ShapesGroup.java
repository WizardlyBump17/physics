package com.wizardlybump17.physics.three.group;

import com.wizardlybump17.physics.Id;
import com.wizardlybump17.physics.three.Rotatable;
import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ShapesGroup implements Rotatable {

    private final @NotNull Id id;

    private final @NotNull Map<Id, Shape> shapes = new HashMap<>();
    private final @NotNull Map<Id, Shape> transformedShapes = new HashMap<>();

    private final @NotNull Vector3D position;
    private final @NotNull Vector3D transformedPosition;

    private final @NotNull Vector3D pivot;
    private final @NotNull Vector3D rotation;

    private ShapesGroup(@NotNull Id id,
                        @NotNull Map<Id, Shape> shapes, @NotNull Map<Id, Shape> transformedShapes,
                        @NotNull Vector3D position, @NotNull Vector3D transformedPosition,
                        @NotNull Vector3D pivot, @NotNull Vector3D rotation) {
        this.id = id;

        this.shapes.putAll(shapes);
        this.transformedShapes.putAll(transformedShapes);

        this.position = position;
        this.transformedPosition = transformedPosition;

        this.pivot = pivot;
        this.rotation = rotation;
    }

    /**
     * @param id
     * @param shapes
     * @param position the center of the group
     * @param pivot    the point around which the group rotates
     * @param rotation
     */
    public ShapesGroup(@NotNull Id id, @NotNull Map<Id, Shape> shapes, @NotNull Vector3D position, @NotNull Vector3D pivot, @NotNull Vector3D rotation) {
        this(
                id,
                Map.copyOf(shapes), Map.of(),
                position, position.rotateAround(rotation, pivot),
                pivot, rotation
        );
    }

    public ShapesGroup(@NotNull Id id, @NotNull Map<Id, Shape> shapes) {
        this(
                id,
                shapes,
                shapes.values().stream()
                        .map(Shape::getPosition)
                        .reduce(Vector3D::add)
                        .orElse(Vector3D.ZERO),
                shapes.values().stream()
                        .map(Shape::getPosition)
                        .reduce(Vector3D::add)
                        .orElse(Vector3D.ZERO),
                Vector3D.ZERO
        );
    }

    public @NotNull Id getId() {
        return id;
    }

    public @NotNull Map<Id, Shape> getShapes() {
        return shapes;
    }

    public @NotNull Map<Id, Shape> getTransformedShapes() {
        return transformedShapes;
    }

    public @NotNull Vector3D getPosition() {
        return position;
    }

    public @NotNull Vector3D getTransformedPosition() {
        return transformedPosition;
    }

    public @NotNull ShapesGroup setPosition(@NotNull Vector3D position) {
        return new ShapesGroup(
                id,
                shapes, transformShapes(shapes, position, pivot, rotation),
                position, position.rotateAround(position, pivot),
                pivot, rotation
        );
    }

    @Override
    public @NotNull Vector3D getRotation() {
        return rotation;
    }

    @Override
    public @NotNull ShapesGroup setRotation(@NotNull Vector3D rotation) {
        return new ShapesGroup(
                id,
                shapes, transformShapes(shapes, position, pivot, rotation),
                position, position.rotateAround(position, pivot),
                pivot, rotation
        );
    }

    @Override
    public @NotNull Vector3D getPivot() {
        return pivot;
    }

    @Override
    public @NotNull ShapesGroup setPivot(@NotNull Vector3D pivot) {
        return new ShapesGroup(
                id,
                shapes, transformShapes(shapes, position, pivot, rotation),
                position, position.rotateAround(position, pivot),
                pivot, rotation
        );
    }

    public static @NotNull Map<Id, Shape> transformShapes(@NotNull Map<Id, Shape> shapes, @NotNull Vector3D position, @NotNull Vector3D pivot, @NotNull Vector3D rotation) {
        Map<Id, Shape> transformedShapes = new HashMap<>();
        shapes.forEach((id, shape) -> transformedShapes.put(id, shape));
        return transformedShapes;
    }
}
