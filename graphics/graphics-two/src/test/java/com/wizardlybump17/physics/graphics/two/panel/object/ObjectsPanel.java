package com.wizardlybump17.physics.graphics.two.panel.object;

import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseListener;
import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseMotionListener;
import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.container.PhysicsObjectContainer;
import com.wizardlybump17.physics.two.intersection.Intersection;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ObjectsPanel extends JPanel {

    private final transient @NotNull Map<Integer, PanelObject> shapes = new HashMap<>();
    private transient PhysicsObjectContainer objectContainer;
    private transient @Nullable PanelObject selectedShape;

    public ObjectsPanel() {
        addMouseListener(new ShapePanelMouseListener(this));
        addMouseMotionListener(new ShapePanelMouseMotionListener(this));
    }

    @SuppressWarnings("unchecked")
    @Override
    public void paint(@NotNull Graphics graphics) {
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
        Intersection intersection = staticObject.getShape().intersect(movingObject.getShape());
        if (intersection.intersects()) {
            staticObject.addIntersection(movingObject.getId(), intersection);
            movingObject.addIntersection(staticObject.getId(), intersection);
            return;
        }

        staticObject.removeIntersection(movingObject.getId());
        movingObject.removeIntersection(staticObject.getId());
    }

    public @NotNull Intersection getIntersection(@NotNull Shape shape, int id) {
        for (PanelObject otherPanelObject : shapes.values()) {
            if (otherPanelObject.getId() == id)
                continue;

            Intersection intersection = otherPanelObject.getShape().intersect(shape);
            if (intersection.intersects())
                return intersection;
        }
        return Intersection.EMPTY;
    }

    public <R extends ShapeRenderer<?>> void addObject(@NotNull PhysicsObject object, @NotNull R renderer) {
        PanelObject panelObject = new PanelObject(object, renderer);
        shapes.put(panelObject.getId(), panelObject);
        renderer.setPanelObject(panelObject);

        objectContainer.addObject(object);
    }

    public void teleportToSafePositions() {
        for (PanelObject panelObject : shapes.values()) {
            int objectId = panelObject.getId();

            for (Map.Entry<Integer, Intersection> entry : panelObject.getIntersecting().entrySet()) {
                int id = entry.getKey();
                Intersection intersection = entry.getValue();

                if (!intersection.intersects())
                    continue;

                PhysicsObject object = panelObject.getObject();
                if (!object.getShape().equals(intersection.getMovingShape()))
                    continue;

                object.teleport(intersection.getSafePosition());

                panelObject.removeIntersection(id);
                shapes.get(id).removeIntersection(objectId);
            }
        }
    }

    public @NotNull Map<Integer, PanelObject> getShapes() {
        return shapes;
    }

    public PhysicsObjectContainer getObjectContainer() {
        return objectContainer;
    }

    public void setObjectContainer(@NotNull PhysicsObjectContainer objectContainer) {
        this.objectContainer = objectContainer;
    }

    public @Nullable PanelObject getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(@Nullable PanelObject selectedShape) {
        this.selectedShape = selectedShape;
    }
}
