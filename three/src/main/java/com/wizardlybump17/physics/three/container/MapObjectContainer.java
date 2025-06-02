package com.wizardlybump17.physics.three.container;

import com.wizardlybump17.physics.three.group.ShapesGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class MapObjectContainer extends ShapesGroupContainer {

    private final @NotNull Map<Integer, ShapesGroup> groups = new HashMap<>();

    public MapObjectContainer(@NotNull UUID id) {
        super(id);
    }

    @Override
    public @Nullable ShapesGroup getGroup(int id) {
        return groups.get(id);
    }

    @Override
    public @NotNull Collection<ShapesGroup> getShapesGroups() {
        return Collections.unmodifiableCollection(groups.values());
    }

    @Override
    public void addGroup(@NotNull ShapesGroup group) {
        groups.put(group.getId(), group);
    }

    @Override
    public boolean hasGroup(int groupId) {
        return groups.containsKey(groupId);
    }

    @Override
    public void removeGroup(int groupId) {
        groups.remove(groupId);
    }

    @Override
    public void tick() {
        for (ShapesGroup group : groups.values())
            group.tick();
    }
}
