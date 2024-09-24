package com.wizardlybump17.physics.graphics.two.panel.shape;

import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseListener;
import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseMotionListener;
import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.intersection.Intersection;
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
        addMouseMotionListener(new ShapePanelMouseMotionListener(this, false));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void paint(@NonNull Graphics graphics) {
        super.paintComponent(graphics);

        intersections();

        shapes.values().forEach(panelShape -> ((ShapeRenderer<Shape>) panelShape.getRenderer()).render(graphics, panelShape.getShape()));
        if (selectedShape != null)
            ((ShapeRenderer<Shape>) selectedShape.getRenderer()).render(graphics, selectedShape.getShape());
    }

    public void intersections() {
        for (PanelShape shape : shapes.values()) {
            for (PanelShape anotherShape : shapes.values()) {
                if (shape.getId() == anotherShape.getId())
                    continue;

                Intersection intersection = shape.getShape().intersect(anotherShape.getShape());
                if (intersection.intersects()) {
                    shape.getIntersecting().put(anotherShape.getId(), intersection);
                    anotherShape.getIntersecting().put(shape.getId(), intersection);
                    continue;
                }

                shape.getIntersecting().remove(anotherShape.getId());
                anotherShape.getIntersecting().remove(shape.getId());
            }
        }
    }

    public @NonNull Intersection getIntersection(@NonNull Shape shape, int id) {
        for (PanelShape otherPanelShape : shapes.values()) {
            if (otherPanelShape.getId() == id)
                continue;

            Intersection intersection = otherPanelShape.getShape().intersect(shape);
            if (intersection.intersects())
                return intersection;
        }
        return Intersection.EMPTY;
    }

    public <S extends Shape, R extends ShapeRenderer<? extends S>> void addShape(@NonNull S shape, @NonNull R renderer) {
        PanelShape panelShape = new PanelShape(shape, renderer);
        shapes.put(panelShape.getId(), panelShape);
        renderer.setPanelShape(panelShape);
    }
}
