package com.wizardlybump17.physics.graphics.two.game.ball.ball;

import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.util.MathUtil;
import org.jetbrains.annotations.NotNull;

public class BallPhysics extends Physics {

    public static final @NotNull Vector2D INITIAL_SPEED = new Vector2D(0, 100);
    public static final double MAX_SPEED = new Vector2D(200, 0).length();

    public BallPhysics(@NotNull Ball object, @NotNull Vector2D velocity, @NotNull Vector2D acceleration) {
        super(object, velocity, acceleration);
        setAcceleration(INITIAL_SPEED);
    }

    public BallPhysics(@NotNull Ball object) {
        super(object);
        setAcceleration(INITIAL_SPEED);
    }

    @Override
    public @NotNull Ball getObject() {
        return (Ball) super.getObject();
    }

    @Override
    public void setVelocity(@NotNull Vector2D velocity) {
        if (velocity.lengthSquared() > MathUtil.square(MAX_SPEED))
            velocity = velocity.normalize().multiply(MAX_SPEED);
        super.setVelocity(velocity);
    }
}
