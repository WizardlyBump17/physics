package com.wizardlybump17.physics.two.physics.object;

import com.wizardlybump17.physics.two.container.BaseObjectContainer;
import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PhysicsObject extends BaseObject {

    private @NotNull Physics physics;
    protected final @NotNull Map<Integer, Collision> collisions = new HashMap<>();

    public PhysicsObject(@NotNull BaseObjectContainer container, int id, @NotNull Shape shape, @NotNull Physics physics) {
        super(container, id, shape);
        this.physics = physics;
    }

    public PhysicsObject(@NotNull BaseObjectContainer container, int id, @NotNull Shape shape) {
        super(container, id, shape);
        this.physics = new Physics(this);
    }

    public @NotNull Physics getPhysics() {
        return physics;
    }

    public void setPhysics(@NotNull Physics physics) {
        this.physics = physics;
    }

    @Override
    public void tick(double deltaTime) {
        super.tick(deltaTime);
        handleCollisions();
        physics.tick(deltaTime);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        if (!super.equals(o))
            return false;
        PhysicsObject that = (PhysicsObject) o;
        return Objects.equals(physics, that.physics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), physics);
    }

    @Override
    public String toString() {
        return "PhysicsObject{" +
                "physics=" + physics +
                "} " + super.toString();
    }

    protected void handleCollisions() {
    }

    /**
     * <p>
     * This method is called when this object collides with another one.
     * </p>
     *
     * @param other        the {@link BaseObject} is colliding with this one
     * @param intersection the {@link Intersection}
     */
    public void onCollide(@NotNull BaseObject other, @NotNull Intersection intersection) {
        collisions.put(other.getId(), new Collision(other, intersection));
    }

    public @NotNull Map<Integer, Collision> getCollisions() {
        return Map.copyOf(collisions);
    }

    public boolean isCollidingWith(@NotNull BaseObject other) {
        return collisions.containsKey(other.getId());
    }

    public void onCollisionStop(@NotNull BaseObject other) {
        collisions.remove(other.getId());
    }

    public boolean hasCollisions() {
        return !collisions.isEmpty();
    }

    public record Collision(@NotNull BaseObject object, @NotNull Intersection intersection) {
    }
}
