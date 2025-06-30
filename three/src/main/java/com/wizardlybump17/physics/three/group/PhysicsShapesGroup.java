package com.wizardlybump17.physics.three.group;

import com.wizardlybump17.physics.Id;
import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.container.ShapesGroupsContainer;
import com.wizardlybump17.physics.three.physics.Acceleration;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.*;

public class PhysicsShapesGroup extends ShapesGroup {

    private final @NotNull Set<Integer> collidingWith = new HashSet<>();
    private final @NotNull Map<Id, Acceleration> accelerations = new HashMap<>();
    private @NotNull Vector3D velocity;
    private @NotNull Vector3D rotation;

    public PhysicsShapesGroup(@NotNull ShapesGroupsContainer container, @NotNull List<Shape> shapes) {
        this(container, shapes, Vector3D.ZERO, Vector3D.ZERO, Vector3D.ZERO);
    }

    public PhysicsShapesGroup(@NotNull ShapesGroupsContainer container, @NotNull List<Shape> shapes, @NotNull Collection<Acceleration> accelerations, @NotNull Vector3D velocity, @NotNull Vector3D rotation) {
        super(container, shapes);
        accelerations.forEach(this::addAcceleration);
        this.velocity = velocity;
        this.rotation = rotation;
    }

    public PhysicsShapesGroup(@NotNull ShapesGroupsContainer container, @NotNull List<Shape> shapes, @NotNull Vector3D acceleration, @NotNull Vector3D velocity, @NotNull Vector3D rotation) {
        this(
                container,
                shapes,
                Collections.singletonList(Acceleration.generic(acceleration)),
                velocity,
                rotation
        );
    }

    @Override
    public boolean isPassable() {
        return false;
    }

    @Override
    public boolean isCollidingWith(@NotNull Shape shape) {
        for (Shape thisShape : getShapes())
            if (thisShape.intersects(shape))
                return true;
        return false;
    }

    @Override
    public boolean isCollidingWith(@NotNull ShapesGroup other) {
        return collidingWith.contains(other.getId()) || super.isCollidingWith(other);
    }

    @Override
    protected void onCollide(@NotNull ShapesGroup otherGroup) {
        collidingWith.add(otherGroup.getId());

        setTotalAcceleration(Vector3D.ZERO);
        setVelocity(Vector3D.ZERO);
    }

    @Override
    protected void onStopColliding(@NotNull ShapesGroup otherGroup) {
        collidingWith.remove(otherGroup.getId());
    }

    /**
     * @return the total acceleration of this group, in meters per tick
     */
    public @NotNull Vector3D getTotalAcceleration() {
        Vector3D totalAcceleration = Vector3D.ZERO;
        for (Acceleration acceleration : accelerations.values())
            totalAcceleration = totalAcceleration.add(acceleration.acceleration());
        return totalAcceleration;
    }

    /**
     * @param acceleration the acceleration to set, in meters per tick
     */
    public void setTotalAcceleration(@NotNull Vector3D acceleration) {
        accelerations.clear();
        setAcceleration(Acceleration.generic(acceleration));
    }

    public @Nullable Acceleration getAcceleration(@NotNull Id id) {
        return accelerations.get(id);
    }

    public void addAcceleration(@NotNull Acceleration acceleration) {
        Acceleration existing = getAcceleration(acceleration.id());
        if (existing != null)
            acceleration = acceleration.add(existing.acceleration());
        setAcceleration(acceleration);
    }

    public void subtractAcceleration(@NotNull Acceleration acceleration) {
        Acceleration existing = getAcceleration(acceleration.id());
        if (existing != null)
            acceleration = acceleration.subtract(existing.acceleration());
        setAcceleration(acceleration);
    }

    public void setAcceleration(@NotNull Acceleration acceleration) {
        accelerations.put(acceleration.id(), acceleration);
    }

    public void clearAcceleration(@NotNull Id id) {
        accelerations.remove(id);
    }

    public void clearAccelerations() {
        accelerations.clear();
    }

    public @NotNull @UnmodifiableView Map<Id, Acceleration> getAccelerations() {
        return Collections.unmodifiableMap(accelerations);
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
        velocity = getMaxMovement(velocity.add(getTotalAcceleration()));
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

    public @NotNull Vector3D getRotation() {
        return rotation;
    }

    public void setRotation(@NotNull Vector3D rotation) {
        this.rotation = rotation;
    }
}
