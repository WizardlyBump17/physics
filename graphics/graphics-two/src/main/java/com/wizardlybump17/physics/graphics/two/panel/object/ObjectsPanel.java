package com.wizardlybump17.physics.graphics.two.panel.object;

import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseListener;
import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseMotionListener;
import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.position.Vector2D;
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
public class ObjectsPanel extends JPanel {

    private final transient @NonNull Map<Integer, PanelObject> shapes = new HashMap<>();
    private transient @Nullable PanelObject selectedShape;

    public ObjectsPanel() {
        addMouseListener(new ShapePanelMouseListener(this));
        addMouseMotionListener(new ShapePanelMouseMotionListener(this, false));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void paint(@NonNull Graphics graphics) {
        super.paintComponent(graphics);

        intersections();
        teleportToSafePositions();

        shapes.values().forEach(panelShape -> ((ShapeRenderer<Shape>) panelShape.getRenderer()).render(graphics, panelShape.getShape()));
        if (selectedShape != null)
            ((ShapeRenderer<Shape>) selectedShape.getRenderer()).render(graphics, selectedShape.getShape());
    }

    public void intersections() {
        for (PanelObject shape : shapes.values()) {
            for (PanelObject anotherShape : shapes.values()) {
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
        for (PanelObject otherPanelObject : shapes.values()) {
            if (otherPanelObject.getId() == id)
                continue;

            Intersection intersection = otherPanelObject.getShape().intersect(shape);
            if (intersection.intersects())
                return intersection;
        }
        return Intersection.EMPTY;
    }

    public <R extends ShapeRenderer<?>> void addShape(@NonNull PhysicsObject object, @NonNull R renderer) {
        PanelObject panelObject = new PanelObject(object, renderer);
        shapes.put(panelObject.getId(), panelObject);
        renderer.setPanelObject(panelObject);
    }

    public void tick(long lastTick) {
        for (PanelObject panelObject : shapes.values()) {
            PhysicsObject object = panelObject.getObject();

            Physics physics = object.getPhysics();
            if (panelObject.isSelected()) {
                physics.setAcceleration(Vector2D.ZERO);
                physics.setVelocity(Vector2D.ZERO);
            } else
                physics.setAcceleration(new Vector2D(0, 9.8));

            object.tick((System.currentTimeMillis() - lastTick) / 1000.0);
        }

        repaint();
    }

    public void teleportToSafePositions() {
        for (PanelObject panelObject : shapes.values()) {
            for (Intersection intersection : panelObject.getIntersecting().values()) {
                if (!intersection.intersects())
                    continue;

                PhysicsObject object = panelObject.getObject();
                if (object.getShape().equals(intersection.getMovingShape()))
                    object.teleport(intersection.getSafePosition());
            }
        }
    }
}
