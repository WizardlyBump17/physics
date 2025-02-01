package com.wizardlybump17.physics.two.physics;

import com.wizardlybump17.physics.two.Constants;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Physics {

    public static final double GRAVITATIONAL_CONSTANT = 9.8;
    public static final @NotNull Vector2D GRAVITY_VECTOR = new Vector2D(0, GRAVITATIONAL_CONSTANT);

    private @NotNull PhysicsObject object;
    private @NotNull Vector2D velocity;
    private @NotNull Vector2D acceleration;

    public Physics(@NotNull PhysicsObject object, @NotNull Vector2D velocity, @NotNull Vector2D acceleration) {
        this.object = object;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public Physics(@NotNull Vector2D velocity, @NotNull Vector2D acceleration) {
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public Physics(@NotNull PhysicsObject object) {
        this.object = object;
        velocity = Vector2D.ZERO;
        acceleration = Vector2D.ZERO;
    }

    public Physics() {
        velocity = Vector2D.ZERO;
        acceleration = Vector2D.ZERO;
    }

    public void setObject(@NotNull PhysicsObject object) {
        this.object = object;
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

    public void addVelocity(@NotNull Vector2D velocity) {
        setVelocity(this.velocity.add(velocity));
    }

    public void subtractVelocity(@NotNull Vector2D velocity) {
        setVelocity(this.velocity.subtract(velocity));
    }

    public @NotNull Vector2D getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(@NotNull Vector2D acceleration) {
        this.acceleration = acceleration;
    }

    public void addAcceleration(@NotNull Vector2D acceleration) {
        setAcceleration(this.acceleration.add(acceleration));
    }

    public void subtractAcceleration(@NotNull Vector2D acceleration) {
        setAcceleration(this.acceleration.subtract(acceleration));
    }

    public void tick() {
        setVelocity(velocity.add(acceleration.divide(Constants.TICKS_PER_SECOND)));
        object.teleport(object.getPosition().add(velocity.divide(Constants.TICKS_PER_SECOND)));
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

    public @NotNull Vector2D getEffectiveVelocity() {
        return velocity.add(acceleration);
    }

    public @NotNull Vector2D getTargetPosition() {
        return object.getPosition().add(getEffectiveVelocity());
    }
}
