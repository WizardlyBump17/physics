package com.wizardlybump17.physics.graphics.two.panel.shape;

import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseListener;
import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseMotionListener;
import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.shape.Shape;
import lombok.Getter;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Getter
public class ShapesPanel extends JPanel {

    private final @NonNull Map<Integer, PanelShape> shapes = new HashMap<>();

    public ShapesPanel() {
        addMouseListener(new ShapePanelMouseListener(this));
        addMouseMotionListener(new ShapePanelMouseMotionListener(this));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void paintComponent(@NonNull Graphics graphics) {
        super.paintComponent(graphics);

        shapes.values().forEach(panelShape -> ((ShapeRenderer<Shape>) panelShape.getRenderer()).render(graphics, panelShape.getShape()));
    }

    public <S extends Shape, R extends ShapeRenderer<? extends S>> void addShape(@NonNull S shape, @NonNull R renderer) {
        PanelShape panelShape = new PanelShape(shape, renderer);
        shapes.put(panelShape.getId(), panelShape);
        renderer.setPanelShape(panelShape);
    }
}
