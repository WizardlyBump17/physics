package com.wizardlybump17.physics.two.object;

import com.wizardlybump17.physics.two.container.BaseObjectContainer;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class BaseObject {

    private final int id;
    private @NotNull Shape shape;
    private final @NotNull BaseObjectContainer container;

    public BaseObject(@NotNull BaseObjectContainer container, int id, @NotNull Shape shape) {
        this.container = container;
        this.id = id;
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

    public @NotNull Vector2D getPosition() {
        return shape.getPosition();
    }

    public void teleport(@NotNull Vector2D position) {
        shape = shape.at(position);
    }

    public void tick(double deltaTime) {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
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

    public @NotNull BaseObjectContainer getContainer() {
        return container;
    }
}
