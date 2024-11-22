package com.wizardlybump17.physics.two.object;

import com.wizardlybump17.physics.two.container.BaseObjectContainer;
import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class BaseObject {

    private final int id;
    private @NotNull Shape shape;
    private final @NotNull Map<Integer, Collision> collisions = new HashMap<>();
    private final @NotNull BaseObjectContainer container;

    public BaseObject(@NotNull BaseObjectContainer container, int id, @NotNull Shape shape) {
        this.container = container;
        this.id = id;
        this.shape = shape;
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

    public @NotNull Vector2D getPosition() {
        return shape.getPosition();
    }

    public void teleport(@NotNull Vector2D position) {
        shape = shape.at(position);
    }

    public void tick(double deltaTime) {
        handleCollisions();
    }

    protected void handleCollisions() {
        container.collisions(this);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        BaseObject that = (BaseObject) o;
        return id == that.id && Objects.equals(shape, that.shape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shape);
    }

    @Override
    public String toString() {
        return "BaseObject{" +
                "id=" + id +
                ", shape=" + shape +
                '}';
    }

    /**
     * <p>
     *     This method is called when this object collides with another one.
     *     The {@link Intersection#getMovingShape()} is this object and the {@link Intersection#getStaticShape()} is the other object.
     * </p>
     * @param other the {@link BaseObject} that will be collided with
     * @param intersection the {@link Intersection}
     */
    public void onCollide(@NotNull BaseObject other, @NotNull Intersection intersection) {
        collisions.put(other.getId(), new Collision(other, intersection));
    }

    /**
     * <p>
     *     This method is called when this object is collided by another one.
     *     The {@link Intersection#getStaticShape()} is this object and the {@link Intersection#getMovingShape()} is the other object.
     * </p>
     * @param collider the {@link BaseObject} that is colliding with this one
     * @param intersection the {@link Intersection}
     */
    public void onBeingCollided(@NotNull BaseObject collider, @NotNull Intersection intersection) {
        collisions.put(collider.getId(), new Collision(collider, intersection));
    }

    public @NotNull Map<Integer, Collision> getCollisions() {
        return Map.copyOf(collisions);
    }

    protected @NotNull Map<Integer, Collision> getCollisionsInternal() {
        return collisions;
    }

    public void addCollision(@NotNull Collision collision) {
        collisions.put(collision.object.id, collision);
    }

    public boolean isCollidingWith(@NotNull BaseObject other) {
        return collisions.containsKey(other.id);
    }

    public void removeCollision(int id) {
        collisions.remove(id);
    }

    public @NotNull BaseObjectContainer getContainer() {
        return container;
    }

    public void onCollisionStop(@NotNull BaseObject other) {
        removeCollision(other.id);
    }

    public void onStopBeingCollided(@NotNull BaseObject collider) {
        removeCollision(collider.id);
    }

    public boolean hasCollisions() {
        return !collisions.isEmpty();
    }

    public record Collision(@NotNull BaseObject object, @NotNull Intersection intersection) {
    }
}
