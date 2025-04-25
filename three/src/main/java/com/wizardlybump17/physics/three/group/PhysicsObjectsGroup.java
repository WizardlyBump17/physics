package com.wizardlybump17.physics.three.group;

import com.wizardlybump17.physics.three.container.BaseObjectContainer;
import com.wizardlybump17.physics.three.object.BaseObject;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class PhysicsObjectsGroup extends AbstractObjectsGroup {

    private final @NotNull Set<Integer> collidingWith = new HashSet<>();

    public PhysicsObjectsGroup(@NotNull BaseObjectContainer container, @NotNull Map<Integer, BaseObject> objects) {
        super(container, objects);
    }

    @Override
    public abstract boolean isPassable();

    @Override
    public boolean isCollidingWith(@NotNull BaseObject other) {
        return collidingWith.contains(other.getId());
    }

    @Override
    protected void onCollide(@NotNull ObjectsGroup otherGroup) {
        collidingWith.add(otherGroup.getId());
    }

    @Override
    protected void onStopColliding(@NotNull ObjectsGroup otherGroup) {
        collidingWith.remove(otherGroup.getId());
    }
}
