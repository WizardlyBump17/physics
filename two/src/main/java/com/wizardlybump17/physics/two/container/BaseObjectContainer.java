package com.wizardlybump17.physics.two.container;

import com.wizardlybump17.physics.Pair;
import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.Timeable;
import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public abstract class BaseObjectContainer implements Tickable, Timeable {

    private final @NotNull List<Pair<Integer, Integer>> collisions = new LinkedList<>();
    private final @NotNull List<Pair<Integer, Integer>> endedCollisions = new LinkedList<>();
    private long startedAt;
    private long endedAt;

    @Override
    public void tick() {
        start();

        for (BaseObject object : getObjectsInternal())
            object.tick();
        handleCollisions();

        end();
    }

    @Override
    public void start() {
        startedAt = System.currentTimeMillis();
    }

    @Override
    public void end() {
        endedAt = System.currentTimeMillis();
    }

    @Override
    public long getStartedAt() {
        return startedAt;
    }

    @Override
    public long getEndedAt() {
        return endedAt;
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
     * save some bits in memory, because this method will be used in some internal code like in {@link #tick()}
     */
    protected abstract @NotNull Collection<BaseObject> getObjectsInternal();

    public abstract void clear();

    protected void handleCollisions() {
        collisions.clear();
        endedCollisions.clear();

        collisionBroadPhase();
        collisionNarrowPhase();

        endCollisions();
    }

    protected void collisionBroadPhase() {
        for (BaseObject base : getObjectsInternal()) {
            if (!(base instanceof PhysicsObject baseObject) || !baseObject.isCollidable())
                continue;

            int baseId = base.getId();

            for (BaseObject target : getObjectsInternal()) {
                int targetId = target.getId();
                if (baseId == targetId)
                    continue;

                if (!(target instanceof PhysicsObject targetObject) || !targetObject.isCollidable())
                    continue;

                Pair<Integer, Integer> pair = new Pair<>(baseId, targetId);

                if (!base.getShape().intersects(target.getShape())) {
                    endedCollisions.add(pair);
                    continue;
                }

                collisions.add(pair);
            }
        }
    }

    protected void collisionNarrowPhase() {
        for (Pair<Integer, Integer> pair : collisions) {
            PhysicsObject firstObject = (PhysicsObject) getObject(pair.first());
            PhysicsObject secondObject = (PhysicsObject) getObject(pair.second());

            if (firstObject == null || secondObject == null)
                return;

            firstObject.onCollide(secondObject);
            secondObject.onCollide(firstObject);
        }
    }

    protected void endCollisions() {
        for (Pair<Integer, Integer> pair : endedCollisions) {
            PhysicsObject firstObject = (PhysicsObject) getObject(pair.first());
            PhysicsObject secondObject = (PhysicsObject) getObject(pair.second());

            if (firstObject == null || secondObject == null)
                return;

            if (firstObject.isCollidingWith(secondObject))
                firstObject.onCollisionStop(secondObject);
            if (secondObject.isCollidingWith(firstObject))
                secondObject.onCollisionStop(firstObject);
        }
    }
}
