package com.wizardlybump17.physics.graphics.two.panel.shape;

import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseListener;
import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseMotionListener;
import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.shape.Shape;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ShapesPanel extends JPanel {

    private final @NonNull Map<Integer, PanelShape> shapes = new HashMap<>();
    private @Nullable PanelShape selectedShape;

    public ShapesPanel() {
        addMouseListener(new ShapePanelMouseListener(this));
        addMouseMotionListener(new ShapePanelMouseMotionListener(this));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void paint(@NonNull Graphics graphics) {
        super.paintComponent(graphics);

        for (PanelShape shape : shapes.values()) {
            for (PanelShape anotherShape : shapes.values()) {
                if (shape.getId() == anotherShape.getId())
                    continue;

                if (shape.getShape().intersects(anotherShape.getShape())) {
                    shape.getIntersecting().add(anotherShape.getId());
                    anotherShape.getIntersecting().add(shape.getId());
                    continue;
                }

                shape.getIntersecting().remove(anotherShape.getId());
                anotherShape.getIntersecting().remove(shape.getId());
            }
        }

        shapes.values().forEach(panelShape -> ((ShapeRenderer<Shape>) panelShape.getRenderer()).render(graphics, panelShape.getShape()));
        if (selectedShape != null)
            ((ShapeRenderer<Shape>) selectedShape.getRenderer()).render(graphics, selectedShape.getShape());
    }

    public <S extends Shape, R extends ShapeRenderer<? extends S>> void addShape(@NonNull S shape, @NonNull R renderer) {
        PanelShape panelShape = new PanelShape(shape, renderer);
        shapes.put(panelShape.getId(), panelShape);
        renderer.setPanelShape(panelShape);
    }
}
