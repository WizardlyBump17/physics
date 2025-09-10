package com.wizardlybump17.physics.three.group.test;

import com.wizardlybump17.physics.Id;
import com.wizardlybump17.physics.three.Rotatable;
import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.shape.Shape;
import com.wizardlybump17.physics.util.MapUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Map;

public class ShapesGroup implements Rotatable {

    private final @NotNull Id id;

    private final @Unmodifiable @NotNull Map<Id, Shape> shapes;
    private final @Unmodifiable @NotNull Map<Id, Shape> transformedShapes;

    private final @Nullable ShapesGroup parent;

    private final @NotNull @Unmodifiable Map<Id, ShapesGroup> children;
    private final @Unmodifiable @NotNull Map<Id, ShapesGroup> transformedChildren;

    private final @NotNull Vector3D position;
    private final @NotNull Vector3D transformedPosition;

    private final @NotNull Vector3D pivot;
    private final @NotNull Vector3D rotation;

    private ShapesGroup(@NotNull Id id,
                        @Unmodifiable @NotNull Map<Id, Shape> shapes, @Unmodifiable @NotNull Map<Id, Shape> transformedShapes,
                        @Nullable ShapesGroup parent,
                        @Unmodifiable @NotNull Map<Id, ShapesGroup> children, @Unmodifiable @NotNull Map<Id, ShapesGroup> transformedChildren,
                        @NotNull Vector3D position, @NotNull Vector3D transformedPosition,
                        @NotNull Vector3D pivot, @NotNull Vector3D rotation) {
        this.id = id;

        this.shapes = shapes;
        this.transformedShapes = transformedShapes;

        this.parent = parent;

        this.children = children;
        this.transformedChildren = transformedChildren;

        this.position = position;
        this.transformedPosition = transformedPosition;

        this.pivot = pivot;
        this.rotation = rotation;
    }

    /**
     * @param id
     * @param shapes
     * @param parent
     * @param children
     * @param position the center of the group
     * @param pivot    the point around which the group rotates
     * @param rotation
     */
    public ShapesGroup(@NotNull Id id, @NotNull Map<Id, Shape> shapes, @Nullable ShapesGroup parent, @NotNull Collection<ShapesGroup> children, @NotNull Vector3D position, @NotNull Vector3D pivot, @NotNull Vector3D rotation) {
        this(
                id,
                Map.copyOf(shapes), Map.of(),
                parent,
                Map.copyOf(MapUtil.fromCollection(children, ShapesGroup::getId)), Map.of(),
                position, position.rotateAround(rotation, pivot),
                pivot, rotation
        );
    }

    public @NotNull Id getId() {
        return id;
    }

    public @Unmodifiable @NotNull Map<Id, Shape> getShapes() {
        return shapes;
    }

    public @Unmodifiable @NotNull Map<Id, Shape> getTransformedShapes() {
        return transformedShapes;
    }

    public @Nullable ShapesGroup getParent() {
        return parent;
    }

    public @Unmodifiable @NotNull Map<Id, ShapesGroup> getChildren() {
        return children;
    }

    public @Unmodifiable @NotNull Map<Id, ShapesGroup> getTransformedChildren() {
        return transformedChildren;
    }

    public @NotNull Vector3D getPosition() {
        return position;
    }

    public @NotNull Vector3D getTransformedPosition() {
        return transformedPosition;
    }

    public @NotNull ShapesGroup at(@NotNull Vector3D position) {
        return new ShapesGroup(
                id,
                shapes, Map.of(),
                parent,
                children, Map.of(),
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
                shapes, Map.of(),
                parent,
                children, Map.of(),
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
                shapes, Map.of(),
                parent,
                children, Map.of(),
                position, position.rotateAround(position, pivot),
                pivot, rotation
        );
    }
}
