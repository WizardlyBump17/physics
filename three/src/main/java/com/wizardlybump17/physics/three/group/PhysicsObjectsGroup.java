package com.wizardlybump17.physics.three.group;

import com.wizardlybump17.physics.Constants;
import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.container.BaseObjectContainer;
import com.wizardlybump17.physics.three.object.BaseObject;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class PhysicsObjectsGroup extends AbstractObjectsGroup {

    private final @NotNull Set<Integer> collidingWith = new HashSet<>();
    private @NotNull Vector3D acceleration;
    private @NotNull Vector3D velocity;

    public PhysicsObjectsGroup(@NotNull BaseObjectContainer container, @NotNull Collection<BaseObject> objects) {
        this(container, objects, Vector3D.ZERO, Vector3D.ZERO);
    }

    public PhysicsObjectsGroup(@NotNull BaseObjectContainer container, @NotNull Collection<BaseObject> objects, @NotNull Vector3D acceleration, @NotNull Vector3D velocity) {
        super(container, objects);
        this.acceleration = acceleration;
        this.velocity = velocity;
    }

    @Override
    public boolean isCollidingWith(@NotNull BaseObject other) {
        if (collidingWith.contains(other.getId()))
            return true;

        Shape otherShape = other.getShape();
        for (BaseObject object : getObjects().values())
            if (object.getShape().intersects(otherShape))
                return true;

        return false;
    }

    @Override
    public void onCollide(@NotNull ObjectsGroup otherGroup) {
        collidingWith.add(otherGroup.getId());

        setAcceleration(Vector3D.ZERO);
        setVelocity(Vector3D.ZERO);
    }

    @Override
    public void onStopColliding(@NotNull ObjectsGroup otherGroup) {
        collidingWith.remove(otherGroup.getId());
    }

    public @NotNull Vector3D getAcceleration() {
        return acceleration;
    }

    public void setAcceleration(@NotNull Vector3D acceleration) {
        this.acceleration = acceleration;
    }

    public @NotNull Vector3D getVelocity() {
        return velocity;
    }

    public void setVelocity(@NotNull Vector3D velocity) {
        this.velocity = velocity;
    }

    @Override
    public void tick() {
        tickMovement();
        super.tick();
    }

    protected void tickMovement() {
        setVelocity(getVelocity().add(getAcceleration().divide(Constants.TICKS_PER_SECOND)));
        setCenter(getCenter().add(getVelocity().divide(Constants.TICKS_PER_SECOND)));
    }
}
