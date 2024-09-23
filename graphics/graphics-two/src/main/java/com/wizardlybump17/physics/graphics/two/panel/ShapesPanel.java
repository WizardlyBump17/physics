package com.wizardlybump17.physics.graphics.two.panel;

import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.shape.Shape;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ShapesPanel extends JPanel {

    private final @NonNull Map<Shape, ShapeRenderer<?>> shapes = new HashMap<>();

    @SuppressWarnings("unchecked")
    @Override
    protected void paintComponent(@NonNull Graphics graphics) {
        super.paintComponent(graphics);

        shapes.forEach((shape, renderer) -> ((ShapeRenderer<Shape>) renderer).render(graphics, shape));
    }

    public <S extends Shape> void addShape(@NonNull S shape, @NonNull ShapeRenderer<? extends S> renderer) {
        shapes.put(shape, renderer);
    }
}
