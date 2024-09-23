package com.wizardlybump17.physics.graphics.two.panel.shape;

import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Shape;
import lombok.NonNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ShapesPanel extends JPanel {

    private final @NonNull Map<Integer, PanelShape> shapes = new HashMap<>();

    public ShapesPanel() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(@NonNull MouseEvent event) {
                Vector2D point = new Vector2D(event.getX(), event.getY());

                for (PanelShape shape : shapes.values()) {
                    boolean selected = shape.getShape().hasPoint(point);
                    shape.setSelected(selected);

                    if (selected)
                        break;
                }

                repaint();
            }
        });
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void paintComponent(@NonNull Graphics graphics) {
        super.paintComponent(graphics);

        shapes.values().forEach(panelShape -> ((ShapeRenderer<Shape>) panelShape.getRenderer()).render(graphics, panelShape.getShape()));
    }

    public <S extends Shape, R extends ShapeRenderer<? extends S>> void addShape(@NonNull S shape, @NonNull R renderer, @NonNull BiConsumer<R, PanelShape> afterAdd) {
        PanelShape panelShape = new PanelShape(shape, renderer);
        shapes.put(panelShape.getId(), panelShape);
        afterAdd.accept(renderer, panelShape);
    }
}
