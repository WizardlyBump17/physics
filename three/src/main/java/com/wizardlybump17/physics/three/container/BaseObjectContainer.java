package com.wizardlybump17.physics.three.container;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.three.group.ObjectGroup;
import com.wizardlybump17.physics.three.object.BaseObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public abstract class BaseObjectContainer implements Tickable {

    private final @NotNull UUID id;

    public BaseObjectContainer(@NotNull UUID id) {
        this.id = id;
    }

    public @NotNull UUID getId() {
        return id;
    }

    public abstract void addObject(@NotNull BaseObject object);

    public abstract @Nullable BaseObject getObject(int id);

    public abstract boolean hasObject(int id);

    public abstract void removeObject(int id);

    public abstract @NotNull Collection<BaseObject> getLoadedObjects();

    public abstract @NotNull Collection<ObjectGroup> getLoadedGroups();

    public abstract void addGroup(@NotNull ObjectGroup group);

    public abstract boolean hasGroup(int groupId);

    public boolean hasGroup(@NotNull ObjectGroup group) {
        return hasGroup(group.getId());
    }

    public abstract void removeGroup(int groupId);

    public void removeGroup(@NotNull ObjectGroup group) {
        removeGroup(group.getId());
    }

    public abstract int getLoadedObjectsCount();
}
