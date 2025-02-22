package com.wizardlybump17.physics.three.container;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.three.object.BaseObject;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.UUID;

public abstract class BaseObjectContainer implements Tickable {

    private final @NotNull UUID id;
    private int objectCounter;

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

    public abstract int getLoadedObjectsCount();

    @ApiStatus.Internal
    public final int increaseObjectCounter() {
        return objectCounter++;
    }
}
