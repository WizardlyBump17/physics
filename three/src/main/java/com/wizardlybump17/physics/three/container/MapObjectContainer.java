package com.wizardlybump17.physics.three.container;

import com.wizardlybump17.physics.three.group.ObjectsGroup;
import com.wizardlybump17.physics.three.object.BaseObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MapObjectContainer extends BaseObjectContainer {

    private final @NotNull Map<Integer, BaseObject> objects = new HashMap<>();
    private final @NotNull Map<Integer, ObjectsGroup> groups = new HashMap<>();

    public MapObjectContainer(@NotNull UUID id) {
        super(id);
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
    public @NotNull Collection<BaseObject> getObjects() {
        return Collections.unmodifiableCollection(objects.values());
    }

    @Override
    public @NotNull Collection<ObjectsGroup> getObjectsGroups() {
        return Collections.unmodifiableCollection(groups.values());
    }

    @Override
    public void addGroup(@NotNull ObjectsGroup group) {
        groups.put(group.getId(), group);
        for (BaseObject object : group.getObjects().values())
            objects.put(object.getId(), object);
    }

    @Override
    public boolean hasGroup(int groupId) {
        return groups.containsKey(groupId);
    }

    @Override
    public void removeGroup(int groupId) {
        ObjectsGroup removed = groups.remove(groupId);
        if (removed != null) {
            for (BaseObject object : removed.getObjects().values())
                objects.remove(object.getId());
        }
    }

    @Override
    public void tick() {
        for (ObjectsGroup group : groups.values())
            group.tick();
    }
}
