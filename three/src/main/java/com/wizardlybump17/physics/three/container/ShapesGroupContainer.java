package com.wizardlybump17.physics.three.container;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.three.group.ShapesGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public abstract class ShapesGroupContainer implements Tickable {

    private final @NotNull UUID id;

    public ShapesGroupContainer(@NotNull UUID id) {
        this.id = id;
    }

    public @NotNull UUID getId() {
        return id;
    }

    public abstract @NotNull Collection<ShapesGroup> getShapesGroups();

    public abstract void addGroup(@NotNull ShapesGroup group);

    public abstract boolean hasGroup(int groupId);

    public boolean hasGroup(@NotNull ShapesGroup group) {
        return hasGroup(group.getId());
    }

    public abstract void removeGroup(int groupId);

    public void removeGroup(@NotNull ShapesGroup group) {
        removeGroup(group.getId());
    }

    public abstract @Nullable ShapesGroup getGroup(int groupId);
}
