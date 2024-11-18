package com.wizardlybump17.physics.graphics.two.panel.object;

import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.shape.Shape;
import lombok.Data;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@Data
public class PanelObject {

    protected static int currentId;

    private final int id = currentId++;
    private @NonNull PhysicsObject object;
    private @NonNull ShapeRenderer<?> renderer;
    private boolean selected;
    private final @NonNull Map<Integer, Intersection> intersecting = new HashMap<>();

    public @NotNull Shape getShape() {
        return object.getShape();
    }

    public void setShape(@NotNull Shape shape) {
        object.setShape(shape);
    }
}