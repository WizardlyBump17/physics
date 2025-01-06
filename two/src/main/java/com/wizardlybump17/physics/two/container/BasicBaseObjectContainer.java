package com.wizardlybump17.physics.two.container;

import com.wizardlybump17.physics.two.object.BaseObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class BasicBaseObjectContainer extends BaseObjectContainer {

    private final @NotNull Map<Integer, BaseObject> objects = new HashMap<>();

    public BasicBaseObjectContainer(@NotNull UUID id) {
        super(id);
    }

    @Override
    public void addObject(@NotNull BaseObject object) {
        objects.put(object.getId(), object);
    }

    @Override
    public void removeObject(int id) {
        objects.remove(id);
    }

    @Override
    public boolean hasObject(int id) {
        return objects.containsKey(id);
    }

    @Override
    public @Nullable BaseObject getObject(int id) {
        return objects.get(id);
    }

    @Override
    public @NotNull Collection<BaseObject> getObjects() {
        return List.copyOf(objects.values());
    }

    @Override
    protected @NotNull Collection<BaseObject> getObjectsInternal() {
        return objects.values();
    }

    @Override
    public void clear() {
        objects.clear();
    }
}
