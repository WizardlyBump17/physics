package com.wizardlybump17.physics.graphics.two.panel.shape;

import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.shape.Shape;
import lombok.Data;
import lombok.NonNull;

import java.util.HashSet;
import java.util.Set;

@Data
public class PanelShape {

    protected static int currentId;

    private final int id = currentId++;
    private @NonNull Shape shape;
    private @NonNull ShapeRenderer<?> renderer;
    private boolean selected;
    private @NonNull Set<Integer> intersecting = new HashSet<>();
}
