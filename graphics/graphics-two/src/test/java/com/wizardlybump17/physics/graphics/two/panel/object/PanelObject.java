package com.wizardlybump17.physics.graphics.two.panel.object;

import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

public class PanelObject {

    private @NotNull BaseObject object;
    private @NotNull ShapeRenderer<?> renderer;
    private boolean selected;

    public PanelObject(@NotNull BaseObject object, @NotNull ShapeRenderer<?> renderer) {
        this.object = object;
        this.renderer = renderer;
    }

    public @NotNull Shape getShape() {
        return object.getShape();
    }

    public void setShape(@NotNull Shape shape) {
        object.setShape(shape);
    }

    public int getId() {
        return object.getId();
    }

    public @NotNull BaseObject getObject() {
        return object;
    }

    public void setObject(@NotNull BaseObject object) {
        this.object = object;
    }

    public @NotNull ShapeRenderer<?> getRenderer() {
        return renderer;
    }

    public void setRenderer(@NotNull ShapeRenderer<?> renderer) {
        this.renderer = renderer;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean hasIntersections() {
        return object instanceof PhysicsObject physicsObject && physicsObject.hasCollisions();
    }
}
