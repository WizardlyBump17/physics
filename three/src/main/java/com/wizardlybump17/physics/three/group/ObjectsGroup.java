package com.wizardlybump17.physics.three.group;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.container.BaseObjectContainer;
import com.wizardlybump17.physics.three.object.BaseObject;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ObjectsGroup implements Tickable {

    private static final @NotNull AtomicInteger BODY_COUNTER = new AtomicInteger(0); //what is your body count?

    private final int id = BODY_COUNTER.getAndIncrement();
    private final @NotNull BaseObjectContainer container;
    private final @NotNull Map<Integer, BaseObject> objects;

    public ObjectsGroup(@NotNull BaseObjectContainer container, @NotNull Collection<BaseObject> objects) {
        this.container = container;

        Map<Integer, BaseObject> internalObjects = new HashMap<>();
        for (BaseObject object : objects)
            internalObjects.put(object.getId(), object);
        this.objects = Collections.unmodifiableMap(internalObjects);
    }

    public final int getId() {
        return id;
    }

    public final @NotNull BaseObjectContainer getContainer() {
        return container;
    }

    public @NotNull Map<Integer, BaseObject> getObjects() {
        return objects;
    }

    public boolean hasObject(int objectId) {
        return objects.containsKey(objectId);
    }

    public boolean hasObject(@NotNull BaseObject other) {
        return hasObject(other.getId());
    }

    public abstract boolean isPassable();

    public abstract boolean isCollidingWith(@NotNull BaseObject other);

    public boolean isCollidingWith(@NotNull ObjectsGroup otherGroup) {
        for (BaseObject otherObject : otherGroup.getObjects().values())
            if (isCollidingWith(otherObject))
                return true;
        return false;
    }

    protected void onCollide(@NotNull ObjectsGroup otherGroup) {
    }

    protected void onStopColliding(@NotNull ObjectsGroup otherGroup) {
    }

    protected @NotNull Vector3D getCenter() {
        Map<Integer, BaseObject> objects = getObjects();

        Vector3D total = Vector3D.ZERO;
        int totalObjects = objects.size();

        for (BaseObject object : objects.values())
            total = total.add(object.getPosition());

        return total.divide(totalObjects);
    }

    protected void setCenter(@NotNull Vector3D center) {
        for (BaseObject object : getObjects().values()) {
            Vector3D position = object.getPosition();
            object.setShape(object.getShape().at(position.add(center.subtract(position))));
        }
    }

    @Override
    public void tick() {
        tickCollisions();
    }

    protected void tickCollisions() {
        if (isPassable())
            return;

        for (ObjectsGroup otherGroup : getContainer().getObjectsGroups()) {
            if (id == otherGroup.getId())
                continue;

            if (isCollidingWith(otherGroup)) {
                onCollide(otherGroup);
                otherGroup.onCollide(this);
            } else {
                onStopColliding(otherGroup);
                otherGroup.onStopColliding(this);
            }
        }
    }
}
