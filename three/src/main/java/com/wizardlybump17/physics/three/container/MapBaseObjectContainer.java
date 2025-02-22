package com.wizardlybump17.physics.three.container;

import com.wizardlybump17.physics.three.object.BaseObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MapBaseObjectContainer extends BaseObjectContainer {

    private final @NotNull Map<Integer, BaseObject> objects = new HashMap<>();

    public MapBaseObjectContainer(@NotNull UUID id) {
        super(id);
    }

    @Override
    public void addObject(@NotNull BaseObject object) {
        objects.put(object.getId(), object);
    }

    @Override
    public @Nullable BaseObject getObject(int id) {
        return objects.get(id);
    }

    @Override
    public boolean hasObject(int id) {
        return objects.containsKey(id);
    }

    @Override
    public void removeObject(int id) {
        objects.remove(id);
    }

    @Override
    public @NotNull Collection<BaseObject> getLoadedObjects() {
        return Set.copyOf(objects.values());
    }

    @Override
    public int getLoadedObjectsCount() {
        return objects.size();
    }

    @Override
    public void tick() {
        for (BaseObject object : objects.values())
            object.tick();
    }
}
