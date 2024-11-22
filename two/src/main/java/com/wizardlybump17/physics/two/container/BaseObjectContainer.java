package com.wizardlybump17.physics.two.container;

import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.shape.Shape;
import com.wizardlybump17.physics.two.tick.Ticker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public abstract class BaseObjectContainer implements Ticker {

    private long lastTick;

    @Override
    public void run() {
        for (BaseObject object : getObjectsInternal())
            object.tick((System.currentTimeMillis() - lastTick) / 1000.0);

        lastTick = System.currentTimeMillis();
    }

    public long getLastTick() {
        return lastTick;
    }

    public abstract void addObject(@NotNull BaseObject object);

    public abstract void removeObject(int id);

    public abstract boolean hasObject(int id);

    public abstract @Nullable BaseObject getObject(int id);

    public abstract @NotNull Collection<BaseObject> getObjects();

    /**
     * <p>
     * This method returns all {@link BaseObject}s in this container.
     * The difference between this method returns a mutable {@link Collection}, generally the real
     * {@link Collection} holding the {@link BaseObject}s.
     * </p>
     *
     * @return the {@link Collection} of {@link BaseObject}s
     * @implSpec it is recommended that this method returns the actual {@link Collection} of {@link BaseObject}s so we can
     * save some bits in memory, because this method will be used in some internal code like in {@link #run()}
     */
    protected abstract @NotNull Collection<BaseObject> getObjectsInternal();

    public abstract void clear();

    /**
     * <p>
     * Handles the collision for the given object.
     * This method calls the {@link BaseObject#onCollide(BaseObject, Intersection)} of that object,
     * passing the other object (in the loop) and their {@link Intersection}s.
     * </p>
     *
     * @param object the object to handle
     */
    public void collisions(@NotNull BaseObject object) {
        for (BaseObject other : getObjectsInternal()) {
            Shape objectShape = object.getShape();
            Shape otherShape = other.getShape();

            Intersection intersection = objectShape.intersect(otherShape);
            if (!intersection.intersects())
                continue;

            object.onCollide(other, intersection);
        }
    }
}
