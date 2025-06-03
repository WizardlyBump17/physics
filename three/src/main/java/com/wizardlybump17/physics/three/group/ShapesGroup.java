package com.wizardlybump17.physics.three.group;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.container.ShapesGroupsContainer;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class ShapesGroup implements Tickable {

    private static final @NotNull AtomicInteger BODY_COUNTER = new AtomicInteger(0); //what is your body count?

    private final int id = BODY_COUNTER.getAndIncrement();
    private final @NotNull ShapesGroupsContainer container;
    private final @NotNull List<Shape> shapes;

    public ShapesGroup(@NotNull ShapesGroupsContainer container, @NotNull List<Shape> shapes) {
        this.container = container;
        this.shapes = shapes;
    }

    public final int getId() {
        return id;
    }

    public final @NotNull ShapesGroupsContainer getContainer() {
        return container;
    }

    public @NotNull @Unmodifiable List<Shape> getShapes() {
        return Collections.unmodifiableList(shapes);
    }

    public abstract boolean isPassable();

    public abstract boolean isCollidingWith(@NotNull Shape shape);

    public boolean isCollidingWith(@NotNull ShapesGroup otherGroup) {
        for (Shape otherObject : otherGroup.getShapes())
            if (isCollidingWith(otherObject))
                return true;
        return false;
    }

    public boolean isCollidingWithShapes(@NotNull Collection<Shape> shapes) {
        for (Shape shape : shapes)
            if (isCollidingWith(shape))
                return true;
        return false;
    }

    public boolean isCollidingWithObjects(@NotNull Collection<ShapesGroup> groups) {
        for (ShapesGroup group : groups)
            if (isCollidingWith(group))
                return true;
        return false;
    }

    protected void onCollide(@NotNull ShapesGroup otherGroup) {
    }

    protected void onStopColliding(@NotNull ShapesGroup otherGroup) {
    }

    public @NotNull Vector3D getCenter() {
        Vector3D total = Vector3D.ZERO;
        int totalObjects = shapes.size();

        for (Shape shape : shapes)
            total = total.add(shape.getPosition());

        return total.divide(totalObjects);
    }

    public void setCenter(@NotNull Vector3D center) {
        Vector3D currentCenter = getCenter();
        shapes.replaceAll(shape -> {
            Vector3D position = shape.getPosition();
            return shape.at(position.add(center.subtract(currentCenter)));
        });
    }

    @Override
    public void tick() {
        tickCollisions();
    }

    protected void tickCollisions() {
        if (isPassable())
            return;

        for (ShapesGroup otherGroup : getContainer().getShapesGroups()) {
            if (id == otherGroup.getId())
                continue;

            if (isCollidingWith(otherGroup)) {
                onCollide(otherGroup);
                otherGroup.onCollide(this);
            } else {
                onStopColliding(otherGroup);
                otherGroup.onStopColliding(this);
            }
        }
    }
}
