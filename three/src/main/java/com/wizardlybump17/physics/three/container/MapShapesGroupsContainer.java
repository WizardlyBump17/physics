package com.wizardlybump17.physics.three.container;

import com.wizardlybump17.physics.three.group.ShapesGroup;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MapShapesGroupsContainer extends ShapesGroupsContainer {

    private static final @NotNull Logger LOGGER = Logger.getLogger(MapShapesGroupsContainer.class.getName());

    private final @NotNull Map<Integer, ShapesGroup> groups = new HashMap<>();

    public MapShapesGroupsContainer(@NotNull UUID id) {
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
        try {
            for (ShapesGroup group : groups.values())
                group.tick();
        } catch (Throwable throwable) {
            LOGGER.log(Level.SEVERE, "Error while ticking the container " + getId(), throwable);
        }
    }
}
