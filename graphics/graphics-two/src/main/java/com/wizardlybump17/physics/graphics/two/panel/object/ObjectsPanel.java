package com.wizardlybump17.physics.graphics.two.panel.object;

import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseListener;
import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseMotionListener;
import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.container.PhysicsObjectContainer;
import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.shape.Shape;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
public class ObjectsPanel extends JPanel {

    private final transient @NonNull Map<Integer, PanelObject> shapes = new HashMap<>();
    private @NotNull PhysicsObjectContainer objectContainer;
    private transient @Nullable PanelObject selectedShape;

    public ObjectsPanel() {
        addMouseListener(new ShapePanelMouseListener(this));
        addMouseMotionListener(new ShapePanelMouseMotionListener(this, false));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void paint(@NonNull Graphics graphics) {
        Toolkit.getDefaultToolkit().sync();

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
                
                if (shape.isSelected()) {
                    intersect(anotherShape, shape);
                    continue;
                }

                intersect(shape, anotherShape);
            }
        }
    }

    protected void intersect(@NotNull PanelObject staticObject, @NotNull PanelObject movingObject) {
        Map<Integer, Intersection> staticIntersections = staticObject.getIntersecting();
        Map<Integer, Intersection> movingIntersections = movingObject.getIntersecting();

        Intersection intersection = staticObject.getShape().intersect(movingObject.getShape());
        if (intersection.intersects()) {
            staticIntersections.put(movingObject.getId(), intersection);
            movingIntersections.put(staticObject.getId(), intersection);
            return;
        }

        staticIntersections.remove(movingObject.getId());
        movingIntersections.remove(staticObject.getId());
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

    public <R extends ShapeRenderer<?>> void addObject(@NonNull PhysicsObject object, @NonNull R renderer) {
        PanelObject panelObject = new PanelObject(object, renderer);
        shapes.put(panelObject.getId(), panelObject);
        renderer.setPanelObject(panelObject);

        objectContainer.addObject(object);
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
