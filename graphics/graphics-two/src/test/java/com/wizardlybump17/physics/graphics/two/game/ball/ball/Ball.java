package com.wizardlybump17.physics.graphics.two.game.ball.ball;

import com.wizardlybump17.physics.two.container.BaseObjectContainer;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class Ball extends PhysicsObject {

    public Ball(@NotNull BaseObjectContainer container, int id, @NotNull Shape shape, @NotNull BallPhysics physics) {
        super(container, id, shape, physics);
    }

    public Ball(@NotNull BaseObjectContainer container, int id, @NotNull Shape shape) {
        super(container, id, shape);
        setPhysics(new BallPhysics(this));
    }

    @Override
    public @NotNull BallPhysics getPhysics() {
        return (BallPhysics) super.getPhysics();
    }
}
