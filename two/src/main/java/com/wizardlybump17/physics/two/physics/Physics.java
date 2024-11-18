package com.wizardlybump17.physics.two.physics;

import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import org.jetbrains.annotations.NotNull;

public class Physics {

    public void tick(@NotNull PhysicsObject object, double deltaTime) {
        object.setVelocity(object.getVelocity().add(object.getAcceleration()));
        object.teleport(object.getPosition().add(object.getVelocity()));
    }

    @Override
    public String toString() {
        return "Physics{}";
    }
}
