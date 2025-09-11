package com.wizardlybump17.physics.three.group.test;

import com.wizardlybump17.physics.Id;
import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.container.ShapesGroupsContainer;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ContainerShapesGroup {

    private static final @NotNull AtomicInteger ID_COUNTER = new AtomicInteger();

    private final int id = ID_COUNTER.incrementAndGet();
    private final @NotNull ShapesGroupsContainer container;

    private final @NotNull Map<Id, Vector3D> accelerations = new HashMap<>();
    private @NotNull Vector3D velocity;

    private @NotNull Vector3D position;
    private @NotNull Vector3D transformedPosition;

    private @NotNull Vector3D pivot;
    private @NotNull Vector3D rotation;

    private final @NotNull Map<Id, Shape> shapes = new HashMap<>();
    private final @NotNull Map<Id, Shape> transformedShapes = new HashMap<>();

    private ContainerShapesGroup(
            @NotNull ShapesGroupsContainer container,
            @NotNull Map<Id, Vector3D> accelerations, @NotNull Vector3D velocity,
            @NotNull Vector3D position, @NotNull Vector3D transformedPosition,
            @NotNull Vector3D pivot,
            @NotNull Vector3D rotation,
            @NotNull Map<Id, Shape> shapes, @NotNull Map<Id, Shape> transformedShapes) {
        this.container = container;

        this.accelerations.putAll(accelerations);
        this.velocity = velocity;

        this.position = position;
        this.transformedPosition = transformedPosition;

        this.pivot = pivot;

        this.rotation = rotation;

        this.shapes.putAll(shapes);
        this.transformedShapes.putAll(transformedShapes);
    }

    public ContainerShapesGroup(@NotNull ShapesGroupsContainer container, @NotNull Vector3D position, @NotNull Map<Id, Shape> shapes) {
        this(
                container,
                Map.of(), Vector3D.ZERO,
                position, position,
                position,
                Vector3D.ZERO,
                shapes, shapes
        );
    }

    public ContainerShapesGroup(
            @NotNull ShapesGroupsContainer container,
            @NotNull Map<Id, Vector3D> accelerations, @NotNull Vector3D velocity,
            @NotNull Vector3D position,
            @NotNull Vector3D pivot,
            @NotNull Vector3D rotation,
            @NotNull Map<Id, Shape> shapes) {
        this(
                container,
                accelerations, velocity,
                position, position.rotateAround(rotation, pivot),
                pivot,
                rotation,
                shapes, shapes
        );
    }

    public int getId() {
        return id;
    }

    public @NotNull ShapesGroupsContainer getContainer() {
        return container;
    }

    public @UnmodifiableView @NotNull Map<Id, Vector3D> getAccelerations() {
        return Collections.unmodifiableMap(accelerations);
    }

    public @NotNull Vector3D getVelocity() {
        return velocity;
    }

    public void setVelocity(@NotNull Vector3D velocity) {
        this.velocity = velocity;
    }

    public @NotNull Vector3D getPosition() {
        return position;
    }

    public void setPosition(@NotNull Vector3D position) {
        this.position = position;
        transformedPosition = position.rotateAround(rotation, pivot);
    }

    public @NotNull Vector3D getTransformedPosition() {
        return transformedPosition;
    }

    public @NotNull Vector3D getPivot() {
        return pivot;
    }

    public void setPivot(@NotNull Vector3D pivot) {
        this.pivot = pivot;
    }

    public @NotNull Vector3D getRotation() {
        return rotation;
    }

    public void setRotation(@NotNull Vector3D rotation) {
        this.rotation = rotation;
        transformedPosition = position.rotateAround(rotation, pivot);
    }

    public @UnmodifiableView @NotNull Map<Id, Shape> getShapes() {
        return Collections.unmodifiableMap(shapes);
    }

    public @UnmodifiableView @NotNull Map<Id, Shape> getTransformedShapes() {
        return Collections.unmodifiableMap(transformedShapes);
    }

    public void tick() {
        tickMovement();
    }

    protected void tickMovement() {
        Vector3D acceleration = accelerations.values().stream()
                .reduce(Vector3D::add)
                .orElse(Vector3D.ZERO);
        setVelocity(velocity.add(acceleration));
        setPosition(position.add(velocity));
    }
}
