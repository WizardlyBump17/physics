package com.wizardlybump17.physics.two.physics.object;

import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PhysicsObject {

    private final int id;
    private @NotNull Shape shape;
    private @NotNull Physics physics;

    public PhysicsObject(int id, @NotNull Shape shape, @NotNull Physics physics) {
        this.id = id;
        this.shape = shape;
        this.physics = physics;
    }

    public PhysicsObject(int id, @NotNull Shape shape) {
        this.id = id;
        this.shape = shape;
        this.physics = new Physics(this, Vector2D.ZERO, Vector2D.ZERO);
    }

    public int getId() {
        return id;
    }

    public @NotNull Vector2D getPosition() {
        return shape.getPosition();
    }

    public void teleport(@NotNull Vector2D position) {
        shape = shape.at(position);
    }

    public @NotNull Shape getShape() {
        return shape;
    }

    public void setShape(@NotNull Shape shape) {
        this.shape = shape;
    }

    public @NotNull Physics getPhysics() {
        return physics;
    }

    public void setPhysics(@NotNull Physics physics) {
        this.physics = physics;
    }

    public void tick(double deltaTime) {
        physics.tick(deltaTime);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        PhysicsObject that = (PhysicsObject) o;
        return id == that.id && Objects.equals(shape, that.shape) && Objects.equals(physics, that.physics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shape, physics);
    }

    @Override
    public String toString() {
        return "PhysicsObject{" +
                "id=" + id +
                ", shape=" + shape +
                ", physics=" + physics +
                '}';
    }
}
