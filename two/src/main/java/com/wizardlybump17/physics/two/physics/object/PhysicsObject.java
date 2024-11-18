package com.wizardlybump17.physics.two.physics.object;

import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PhysicsObject {

    private @NotNull Shape shape;
    private @NotNull Vector2D velocity;
    private @NotNull Vector2D acceleration;
    private @NotNull Physics physics;

    public PhysicsObject(@NotNull Shape shape, @NotNull Vector2D velocity, @NotNull Vector2D acceleration, @NotNull Physics physics) {
        this.shape = shape;
        this.velocity = velocity;
        this.acceleration = acceleration;
        this.physics = physics;
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

    public @NotNull Vector2D getVelocity() {
        return velocity;
    }

    public void setVelocity(@NotNull Vector2D velocity) {
        this.velocity = velocity;
    }

    public @NotNull Vector2D getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(@NotNull Vector2D acceleration) {
        this.acceleration = acceleration;
    }

    public @NotNull Physics getPhysics() {
        return physics;
    }

    public void setPhysics(@NotNull Physics physics) {
        this.physics = physics;
    }

    public void tick(double deltaTime) {
        physics.tick(this, deltaTime);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        PhysicsObject that = (PhysicsObject) o;
        return Objects.equals(shape, that.shape) && Objects.equals(velocity, that.velocity) && Objects.equals(acceleration, that.acceleration)
                && Objects.equals(physics, that.physics);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shape, velocity, acceleration, physics);
    }

    @Override
    public String toString() {
        return "PhysicsObject{" +
                "shape=" + shape +
                ", velocity=" + velocity +
                ", acceleration=" + acceleration +
                ", physics=" + physics +
                '}';
    }
}
