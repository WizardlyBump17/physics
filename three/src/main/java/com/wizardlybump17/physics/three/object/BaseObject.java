package com.wizardlybump17.physics.three.object;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.container.BaseObjectContainer;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseObject implements Tickable {

    private static final @NotNull AtomicInteger OBJECT_COUNTER = new AtomicInteger(0);

    private final int id;
    private @NotNull Shape shape;
    private final @NotNull BaseObjectContainer container;
    protected final @NotNull Map<Integer, BaseObject> collidingWith = new HashMap<>();

    public BaseObject(@NotNull Shape shape, @NotNull BaseObjectContainer container) {
        this.id = OBJECT_COUNTER.getAndIncrement();
        this.shape = shape;
        this.container = container;
    }

    public int getId() {
        return id;
    }

    public @NotNull Shape getShape() {
        return shape;
    }

    public void setShape(@NotNull Shape shape) {
        this.shape = shape;
    }

    public void teleport(@NotNull Vector3D position) {
        shape = shape.at(position);
    }

    public @NotNull Vector3D getPosition() {
        return shape.getPosition();
    }

    public @NotNull BaseObjectContainer getContainer() {
        return container;
    }

    @Override
    public void tick() {
        tickCollisions();
    }

    protected void tickCollisions() {
        for (BaseObject otherObject : getContainer().getLoadedObjects()) {
            if (id == otherObject.id)
                continue;

            if (getShape().intersects(otherObject.getShape())) {
                onCollide(otherObject);
                collidingWith.put(otherObject.getId(), otherObject);
            } else if (isCollidingWith(otherObject)) {
                onStopColliding(otherObject);
                collidingWith.remove(otherObject.getId());
            }
        }
    }

    public @NotNull Map<Integer, BaseObject> getCollidingWith() {
        return Map.copyOf(collidingWith);
    }

    public boolean isCollidingWith(int otherObject) {
        return collidingWith.containsKey(otherObject);
    }

    public boolean isCollidingWith(@NotNull BaseObject otherObject) {
        return isCollidingWith(otherObject.getId());
    }

    public void onCollide(@NotNull BaseObject otherObject) {
    }

    public void onStopColliding(@NotNull BaseObject otherObject) {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseObject that = (BaseObject) o;
        return id == that.id && Objects.equals(shape, that.shape) && Objects.equals(container, that.container);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shape, container);
    }

    @Override
    public String toString() {
        return "BaseObject{" +
                "id=" + id +
                ", shape=" + shape +
                ", container=" + container +
                '}';
    }
}
