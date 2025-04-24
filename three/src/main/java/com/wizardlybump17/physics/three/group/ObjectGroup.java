package com.wizardlybump17.physics.three.group;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.three.container.BaseObjectContainer;
import com.wizardlybump17.physics.three.object.BaseObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ObjectGroup extends Tickable {

    int getId();

    @NotNull BaseObjectContainer getContainer();

    @NotNull Map<Integer, BaseObject> getObjects();

    boolean hasObject(int objectId);

    default boolean hasObject(@NotNull BaseObject other) {
        return hasObject(other.getId());
    }

    boolean isPassable();

    boolean isCollidingWith(@NotNull BaseObject other);

    default boolean isCollidingWith(@NotNull ObjectGroup otherGroup) {
        for (BaseObject otherObject : otherGroup.getObjects().values())
            if (!hasObject(otherObject) && isCollidingWith(otherObject))
                return true;
        return false;
    }
}
