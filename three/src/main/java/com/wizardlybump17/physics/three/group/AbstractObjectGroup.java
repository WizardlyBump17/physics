package com.wizardlybump17.physics.three.group;

import com.wizardlybump17.physics.three.container.BaseObjectContainer;
import com.wizardlybump17.physics.three.object.BaseObject;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractObjectGroup implements ObjectGroup {

    private static final @NotNull AtomicInteger BODY_COUNTER = new AtomicInteger(0); //what is your body count?

    private final int id = BODY_COUNTER.getAndIncrement();
    private final @NotNull BaseObjectContainer container;
    private final @NotNull Map<Integer, BaseObject> objects;

    public AbstractObjectGroup(@NotNull BaseObjectContainer container, @NotNull Map<Integer, BaseObject> objects) {
        this.container = container;
        this.objects = Collections.unmodifiableMap(objects);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull BaseObjectContainer getContainer() {
        return container;
    }

    @Override
    public @NotNull Map<Integer, BaseObject> getObjects() {
        return objects;
    }

    @Override
    public boolean hasObject(int objectId) {
        return objects.containsKey(objectId);
    }

    @Override
    public abstract boolean isPassable();

    @Override
    public abstract boolean isCollidingWith(@NotNull BaseObject other);
}
