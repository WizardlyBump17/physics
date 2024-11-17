package com.wizardlybump17.physics.two.physics.object;

import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PhysicsObject {

    private @NotNull Shape shape;

    public PhysicsObject(@NotNull Shape shape) {
        this.shape = shape;
    }

    public @NotNull Vector2D getPosition() {
        return shape.getPosition();
    }

    public void teleport(@NotNull Vector2D position) {
        shape = shape.at(position);
    }

    public @NotNull Shape getShape() {
        return shape;
    }

    public void setShape(@NotNull Shape shape) {
        this.shape = shape;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        PhysicsObject that = (PhysicsObject) o;
        return Objects.equals(shape, that.shape);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(shape);
    }

    @Override
    public String toString() {
        return "PhysicsObject{" +
                "shape=" + shape +
                '}';
    }
}
