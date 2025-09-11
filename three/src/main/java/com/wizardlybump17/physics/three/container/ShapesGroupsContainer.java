package com.wizardlybump17.physics.three.container;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.three.group.ContainerShapesGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public abstract class ShapesGroupsContainer implements Tickable {

    private final @NotNull UUID id;

    public ShapesGroupsContainer(@NotNull UUID id) {
        this.id = id;
    }

    public @NotNull UUID getId() {
        return id;
    }

    public abstract @NotNull Collection<ContainerShapesGroup> getGroups();

    public abstract void addGroup(@NotNull ContainerShapesGroup group);

    public abstract boolean hasGroup(int groupId);

    public boolean hasGroup(@NotNull ContainerShapesGroup group) {
        return hasGroup(group.getId());
    }

    public abstract void removeGroup(int groupId);

    public void removeGroup(@NotNull ContainerShapesGroup group) {
        removeGroup(group.getId());
    }

    public abstract @Nullable ContainerShapesGroup getGroup(int groupId);
}
