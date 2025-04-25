package com.wizardlybump17.physics.three.group;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.container.BaseObjectContainer;
import com.wizardlybump17.physics.three.object.BaseObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ObjectsGroup extends Tickable {

    int getId();

    @NotNull BaseObjectContainer getContainer();

    @NotNull Map<Integer, BaseObject> getObjects();

    boolean hasObject(int objectId);

    default boolean hasObject(@NotNull BaseObject other) {
        return hasObject(other.getId());
    }

    boolean isPassable();

    boolean isCollidingWith(@NotNull BaseObject other);

    default boolean isCollidingWith(@NotNull ObjectsGroup otherGroup) {
        for (BaseObject otherObject : otherGroup.getObjects().values())
            if (!hasObject(otherObject) && isCollidingWith(otherObject))
                return true;
        return false;
    }

    default void onCollide(@NotNull ObjectsGroup otherGroup) {
    }

    default void onStopColliding(@NotNull ObjectsGroup otherGroup) {
    }

    default @NotNull Vector3D getCenter() {
        Map<Integer, BaseObject> objects = getObjects();

        Vector3D total = Vector3D.ZERO;
        int totalObjects = objects.size();

        for (BaseObject object : objects.values())
            total = total.add(object.getPosition());

        return total.divide(totalObjects);
    }

    default void setCenter(@NotNull Vector3D center) {
        for (BaseObject object : getObjects().values()) {
            Vector3D position = object.getPosition();
            object.setShape(object.getShape().at(position.add(center.subtract(position))));
        }
    }
}
