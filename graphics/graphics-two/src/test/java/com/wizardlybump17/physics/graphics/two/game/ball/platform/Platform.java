package com.wizardlybump17.physics.graphics.two.game.ball.platform;

import com.wizardlybump17.physics.two.container.BaseObjectContainer;
import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class Platform extends PhysicsObject {

    public Platform(@NotNull BaseObjectContainer container, int id, @NotNull Shape shape, @NotNull PlatformPhysics physics) {
        super(container, id, shape, physics);
    }

    public Platform(@NotNull BaseObjectContainer container, int id, @NotNull Shape shape) {
        super(container, id, shape);
        setPhysics(new PlatformPhysics(this));
    }

    @Override
    public @NotNull PlatformPhysics getPhysics() {
        return (PlatformPhysics) super.getPhysics();
    }

    @Override
    public void setPhysics(@NotNull Physics physics) {
        if (!(physics instanceof PlatformPhysics))
            throw new IllegalArgumentException("Expected a " + PlatformPhysics.class.getSimpleName() + " but got " + physics.getClass().getSimpleName());
        super.setPhysics(physics);
    }
}
