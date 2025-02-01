package com.wizardlybump17.physics.graphics.two.game.ball.platform;

import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import org.jetbrains.annotations.NotNull;

public class PlatformPhysics extends Physics {

    private @NotNull Direction direction = Direction.NONE;

    public PlatformPhysics(@NotNull Platform object, @NotNull Vector2D velocity, @NotNull Vector2D acceleration) {
        super(object, velocity, acceleration);
    }

    public PlatformPhysics(@NotNull Platform object) {
        super(object);
    }

    @Override
    public @NotNull Platform getObject() {
        return (Platform) super.getObject();
    }

    @Override
    public void setObject(@NotNull PhysicsObject object) {
        if (!(object instanceof Platform))
            throw new IllegalArgumentException("Expected a " + Platform.class.getSimpleName() + " but got " + object.getClass().getSimpleName());
        super.setObject(object);
    }

    public void moveLeft() {
        direction = Direction.LEFT;
        setVelocity(new Vector2D(-100, 0));
    }

    public void moveRight() {
        direction = Direction.RIGHT;
        setVelocity(new Vector2D(100, 0));
    }

    public void stopMovement() {
        direction = Direction.NONE;
        setVelocity(Vector2D.ZERO);
    }

    public @NotNull Direction getDirection() {
        return direction;
    }

    public void setDirection(@NotNull Direction direction) {
        this.direction = direction;
    }

    public enum Direction {
        LEFT, RIGHT, NONE;
    }
}
