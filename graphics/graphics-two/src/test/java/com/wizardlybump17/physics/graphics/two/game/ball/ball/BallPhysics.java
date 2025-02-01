package com.wizardlybump17.physics.graphics.two.game.ball.ball;

import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.position.Vector2D;
import org.jetbrains.annotations.NotNull;

public class BallPhysics extends Physics {

    public BallPhysics(@NotNull Ball object, @NotNull Vector2D velocity, @NotNull Vector2D acceleration) {
        super(object, velocity, acceleration);
    }

    public BallPhysics(@NotNull Ball object) {
        super(object);
    }

    @Override
    public @NotNull Ball getObject() {
        return (Ball) super.getObject();
    }
}
