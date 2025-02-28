package com.wizardlybump17.physics.three.object;

import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.container.BaseObjectContainer;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class PhysicsObject extends BasicObject {

    private @NotNull Vector3D acceleration;
    private @NotNull Vector3D velocity;

    public PhysicsObject(@NotNull Shape shape, @NotNull BaseObjectContainer container, @NotNull Vector3D acceleration, @NotNull Vector3D velocity) {
        super(shape, container);
        this.acceleration = acceleration;
        this.velocity = velocity;
    }

    public PhysicsObject(@NotNull Shape shape, @NotNull BaseObjectContainer container) {
        this(shape, container, Vector3D.ZERO, Vector3D.ZERO);
    }

    @Override
    public void tick() {
        setVelocity(velocity.add(acceleration.divide(Constants.TICKS_PER_SECOND)));
        teleport(getPosition().add(velocity.divide(Constants.TICKS_PER_SECOND)));
    }

    public void setAcceleration(@NotNull Vector3D acceleration) {
        this.acceleration = acceleration;
    }

    public @NotNull Vector3D getAcceleration() {
        return acceleration;
    }

    public @NotNull Vector3D getVelocity() {
        return velocity;
    }

    public void setVelocity(@NotNull Vector3D velocity) {
        this.velocity = velocity;
    }
}
