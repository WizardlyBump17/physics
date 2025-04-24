package com.wizardlybump17.physics.three.object;

import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class BaseObject {

    private static final @NotNull AtomicInteger OBJECT_COUNTER = new AtomicInteger(0);

    private final int id;
    private @NotNull Shape shape;

    public BaseObject(@NotNull Shape shape) {
        this.id = OBJECT_COUNTER.getAndIncrement();
        this.shape = shape;
    }

    public int getId() {
        return id;
    }

    public @NotNull Shape getShape() {
        return shape;
    }

    public void setShape(@NotNull Shape shape) {
        this.shape = shape;
    }

    public void teleport(@NotNull Vector3D position) {
        shape = shape.at(position);
    }

    public @NotNull Vector3D getPosition() {
        return shape.getPosition();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseObject that = (BaseObject) o;
        return id == that.id && Objects.equals(shape, that.shape);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shape);
    }

    @Override
    public String toString() {
        return "BaseObject{" +
                "id=" + id +
                ", shape=" + shape +
                '}';
    }
}
