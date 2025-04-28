package com.wizardlybump17.physics.three.group;

import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.container.BaseObjectContainer;
import com.wizardlybump17.physics.three.object.BaseObject;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public abstract class PhysicsObjectsGroup extends ObjectsGroup {

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
    protected void onCollide(@NotNull ObjectsGroup otherGroup) {
        collidingWith.add(otherGroup.getId());

        setAcceleration(Vector3D.ZERO);
        setVelocity(Vector3D.ZERO);
    }

    @Override
    protected void onStopColliding(@NotNull ObjectsGroup otherGroup) {
        collidingWith.remove(otherGroup.getId());
    }

    /**
     * @return the acceleration of this group, in meters per tick
     */
    public @NotNull Vector3D getAcceleration() {
        return acceleration;
    }

    /**
     * @param acceleration the acceleration to set, in meters per tick
     */
    public void setAcceleration(@NotNull Vector3D acceleration) {
        this.acceleration = acceleration;
    }

    /**
    * @return the velocity of this group, in meters per tick
    */
    public @NotNull Vector3D getVelocity() {
        return velocity;
    }

    /**
    * @param velocity the velocity to set, in meters per tick
    */
    public void setVelocity(@NotNull Vector3D velocity) {
        this.velocity = velocity;
    }

    @Override
    public void tick() {
        tickMovement();
        super.tick();
    }

    protected void tickMovement() {
        velocity = getMaxMovement(velocity.add(acceleration));
        setCenter(getCenter().add(velocity));
    }

    /**
     * <p>
     * Returns the maximum movement this object group can reach given the original desired movement.
     * An example of this, is when the original movement would cause the object group to go past the ground,
     * so the returned value would be the movement that would keep the object group on the ground.
     * </p>
     * <p>
     * The returned value must be in meters per tick.
     * </p>
     *
     * @param movement the original desired movement
     * @return the maximum movement this object group can reach given the original desired movement.
     */
    public @NotNull Vector3D getMaxMovement(@NotNull Vector3D movement) {
        return movement;
    }
}
