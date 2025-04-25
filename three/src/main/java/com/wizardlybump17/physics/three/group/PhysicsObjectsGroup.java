package com.wizardlybump17.physics.three.group;

import com.wizardlybump17.physics.three.container.BaseObjectContainer;
import com.wizardlybump17.physics.three.object.BaseObject;
import com.wizardlybump17.physics.three.shape.Shape;
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
        if (collidingWith.contains(other.getId()))
            return true;

        Shape otherShape = other.getShape();
        for (BaseObject object : getObjects().values())
            if (object.getShape().intersects(otherShape))
                return true;

        return false;
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
