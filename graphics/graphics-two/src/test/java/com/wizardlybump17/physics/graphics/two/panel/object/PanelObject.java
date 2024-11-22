package com.wizardlybump17.physics.graphics.two.panel.object;

import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class PanelObject {

    protected static int currentId;

    private final int id = currentId++;
    private @NotNull BaseObject object;
    private @NotNull ShapeRenderer<?> renderer;
    private boolean selected;
    private final @NotNull Map<Integer, Intersection> intersecting = new HashMap<>();

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

    public @NotNull Map<Integer, Intersection> getIntersecting() {
        return Map.copyOf(intersecting);
    }

    public void addIntersection(int id, @NotNull Intersection intersection) {
        intersecting.put(id, intersection);
    }

    public boolean hasIntersection(int id) {
        return intersecting.containsKey(id);
    }

    public void removeIntersection(int id) {
        intersecting.remove(id);
    }

    public @Nullable Intersection getIntersection(int id) {
        return intersecting.get(id);
    }

    public void clearIntersections() {
        intersecting.clear();
    }

    public boolean hasIntersections() {
        return !intersecting.isEmpty();
    }

    public int getId() {
        return id;
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
}
