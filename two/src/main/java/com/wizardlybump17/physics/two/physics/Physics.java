package com.wizardlybump17.physics.two.physics;

import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class Physics {

    private final @NotNull PhysicsObject object;

    public Physics(@NotNull PhysicsObject object) {
        this.object = object;
    }

    public @NotNull PhysicsObject getObject() {
        return object;
    }

    public void tick(double deltaTime) {
        object.setVelocity(object.getVelocity().add(object.getAcceleration()));
        object.teleport(object.getPosition().add(object.getVelocity()));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        Physics physics = (Physics) o;
        return object.getId() == physics.object.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(object.getId());
    }

    @Override
    public String toString() {
        return "Physics{" +
                "object=" + object.getId() +
                '}';
    }
}
