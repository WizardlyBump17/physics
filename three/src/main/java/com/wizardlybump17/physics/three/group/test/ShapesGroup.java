package com.wizardlybump17.physics.three.group.test;

import com.wizardlybump17.physics.Id;
import com.wizardlybump17.physics.three.Rotatable;
import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.util.MapUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Map;

/**
 * @param id
 * @param shapes
 * @param parent
 * @param children
 * @param position the center of the group
 * @param pivot the point around which the group rotates
 * @param rotation
 */
public record ShapesGroup(@NotNull Id id,
                          @Unmodifiable @NotNull Map<Id, IdentifiedShape> shapes,
                          @Nullable ShapesGroup parent, @NotNull @Unmodifiable Map<Id, ShapesGroup> children,
                          @NotNull Vector3D position, @NotNull Vector3D pivot, @NotNull Vector3D rotation) implements Rotatable {

    public ShapesGroup {
        shapes = Map.copyOf(shapes);
        children = Map.copyOf(children);
    }

    public ShapesGroup(@NotNull Id id, @NotNull Collection<IdentifiedShape> shapes, @Nullable ShapesGroup parent, @NotNull Collection<ShapesGroup> children, @NotNull Vector3D position, @NotNull Vector3D pivot, @NotNull Vector3D rotation) {
        this(
                id,
                MapUtil.fromCollection(shapes, IdentifiedShape::id),
                parent, MapUtil.fromCollection(children, ShapesGroup::id),
                position, pivot, rotation
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
                shapes,
                parent, children,
                position, pivot, rotation
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
                shapes,
                parent, children,
                position, pivot, rotation
        );
    }
}
