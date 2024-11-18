package com.wizardlybump17.physics.two.physics;

import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Physics {

    private final @NotNull PhysicsObject object;
    private @NotNull Vector2D velocity;
    private @NotNull Vector2D acceleration;

    public Physics(@NotNull PhysicsObject object, @NotNull Vector2D velocity, @NotNull Vector2D acceleration) {
        this.object = object;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public @NotNull PhysicsObject getObject() {
        return object;
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

    public void tick(double deltaTime) {
        velocity = velocity.add(acceleration);
        object.teleport(object.getPosition().add(velocity));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Physics physics = (Physics) o;
        return object.getId() == physics.object.getId() && Objects.equals(velocity, physics.velocity)
                && Objects.equals(acceleration, physics.acceleration);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object.getId(), velocity, acceleration);
    }

    @Override
    public String toString() {
        return "Physics{" +
                "object=" + object.getId() +
                ", velocity=" + velocity +
                ", acceleration=" + acceleration +
                '}';
    }
}
