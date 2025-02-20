package com.wizardlybump17.physics.three.object;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.three.Vector3D;
import com.wizardlybump17.physics.three.container.BaseObjectContainer;
import com.wizardlybump17.physics.three.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public abstract class BaseObject implements Tickable {

    private final int id;
    private @NotNull Shape shape;
    private final @NotNull BaseObjectContainer container;

    public BaseObject(int id, @NotNull Shape shape, @NotNull BaseObjectContainer container) {
        this.id = id;
        this.shape = shape;
        this.container = container;
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

    public @NotNull BaseObjectContainer getContainer() {
        return container;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BaseObject that = (BaseObject) o;
        return id == that.id && Objects.equals(shape, that.shape) && Objects.equals(container, that.container);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shape, container);
    }

    @Override
    public String toString() {
        return "BaseObject{" +
                "id=" + id +
                ", shape=" + shape +
                ", container=" + container +
                '}';
    }
}
