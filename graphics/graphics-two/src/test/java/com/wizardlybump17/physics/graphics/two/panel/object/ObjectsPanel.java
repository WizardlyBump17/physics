package com.wizardlybump17.physics.graphics.two.panel.object;

import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelKeyboardListener;
import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseListener;
import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseMotionListener;
import com.wizardlybump17.physics.graphics.two.renderer.shape.*;
import com.wizardlybump17.physics.two.container.BaseObjectContainer;
import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import com.wizardlybump17.physics.two.shape.Shape;
import com.wizardlybump17.physics.two.shape.rotating.RotatingPolygon;
import com.wizardlybump17.physics.two.shape.rotating.RotatingRectangle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.*;

public class ObjectsPanel extends JPanel {

    private final transient @NotNull Map<Integer, PanelObject> shapes = Collections.synchronizedMap(new HashMap<>());
    private transient BaseObjectContainer objectContainer;
    private transient @Nullable PanelObject selectedShape;
    private transient PhysicsObject fallingBall;
    private transient int objectCount;
    private transient BaseObject rotatingRectangle;

    private final transient List<BaseObject> rotatingPolygons = new ArrayList<>();

    public ObjectsPanel() {
        setFocusable(true);
        requestFocus();

        addMouseListener(new ShapePanelMouseListener(this));
        addMouseMotionListener(new ShapePanelMouseMotionListener(this));
        addKeyListener(new ShapePanelKeyboardListener(this));
    }

    public PhysicsObject getFallingBall() {
        return fallingBall;
    }

    public void regenerate() {
        synchronized (shapes) {
            shapes.clear();
            objectContainer.clear();

            selectedShape = null;

            Random random = new Random();
            Dimension size = getSize();

            for (int i = 0; i < 10; i++) {
                addObject(
                        new PhysicsObject(
                                objectContainer,
                                objectCount++,
                                new Rectangle(
                                        Vector2D.randomVector(random, 0, 0, size.getWidth(), size.getHeight()),
                                        random.nextDouble(90) + 10,
                                        random.nextDouble(90) + 10
                                )
                        ),
                        new RectangleRenderer()
                );

                addObject(
                        new PhysicsObject(
                                objectContainer,
                                objectCount++,
                                new Circle(
                                        Vector2D.randomVector(random, 0, 0, size.getWidth(), size.getHeight()),
                                        random.nextDouble(50) + 10
                                )
                        ),
                        new CircleRenderer()
                );
            }

            fallingBall = new PhysicsObject(
                    objectContainer,
                    objectCount++,
                    new Circle(
                            Vector2D.randomVector(random, 0, 0, size.getWidth(), size.getHeight()),
                            random.nextDouble(50) + 10
                    )
            );
            fallingBall.setPhysics(new FallingBallPhysics(addObject(fallingBall, new CircleRenderer())));

            rotatingRectangle = new BaseObject(
                    objectContainer,
                    objectCount++,
                    new RotatingRectangle(
                            Vector2D.randomVector(random, 0, 0, size.getWidth(), size.getHeight()),
                            50, 30,
                            45
                    )
            );
            addObject(rotatingRectangle, new RotatingRectangleRenderer());

            rotatingPolygons.clear();
            {
                for (int i = 0; i < 2; i++) {
                    Vector2D center = Vector2D.randomVector(random, 0, 0, size.getWidth(), size.getHeight());
                    BaseObject rotatingPolygon = new BaseObject(
                            objectContainer,
                            objectCount++,
                            new RotatingPolygon(
                                    center,
                                    new ArrayList<>(List.of(
                                            new Vector2D(-30, 30),
                                            new Vector2D(30, 30),
                                            new Vector2D(30, -30),
                                            new Vector2D(-30, -30)
                                    )),
                                    0
                            )
                    );
                    addObject(rotatingPolygon, new RotatingPolygonRenderer());
                    rotatingPolygons.add(rotatingPolygon);
                }
            }
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void paint(@NotNull Graphics graphics) {
        synchronized (shapes) {
            Toolkit.getDefaultToolkit().sync();

            super.paintComponent(graphics);

            for (int i = 0; i < rotatingPolygons.size(); i++) {
                for (int i1 = 0; i1 < rotatingPolygons.size(); i1++) {
                    if (i == i1)
                        continue;

                    BaseObject object1 = rotatingPolygons.get(i);
                    BaseObject object2 = rotatingPolygons.get(i1);

                    RotatingPolygon resolved = RotatingPolygon.resolveOverlap((RotatingPolygon) object1.getShape(), (RotatingPolygon) object2.getShape());
                    boolean collision = resolved != null;

                    PanelObject panelObject1 = shapes.get(object1.getId());
                    PanelObject panelObject2 = shapes.get(object2.getId());

                    panelObject1.setHasCollisions(panelObject1.hasIntersections() | collision);
                    panelObject2.setHasCollisions(panelObject2.hasIntersections() | collision);

                    if (collision)
                        object1.setShape(resolved);
                }
            }

            shapes.values().forEach(panelShape -> ((ShapeRenderer<Shape>) panelShape.getRenderer()).render(graphics, panelShape.getShape()));
            if (selectedShape != null)
                ((ShapeRenderer<Shape>) selectedShape.getRenderer()).render(graphics, selectedShape.getShape());

            for (BaseObject rotatingPolygon : rotatingPolygons) {
                shapes.get(rotatingPolygon.getId()).setHasCollisions(false);
            }
        }
    }

    public <R extends ShapeRenderer<?>> @NotNull PanelObject addObject(@NotNull BaseObject object, @NotNull R renderer) {
        PanelObject panelObject = new PanelObject(object, renderer);
        shapes.put(panelObject.getId(), panelObject);
        renderer.setPanelObject(panelObject);

        objectContainer.addObject(object);

        return panelObject;
    }

    public @NotNull Map<Integer, PanelObject> getShapes() {
        return shapes;
    }

    public BaseObjectContainer getObjectContainer() {
        return objectContainer;
    }

    public void setObjectContainer(@NotNull BaseObjectContainer objectContainer) {
        this.objectContainer = objectContainer;
    }

    public @Nullable PanelObject getSelectedShape() {
        return selectedShape;
    }

    public void setSelectedShape(@Nullable PanelObject selectedShape) {
        this.selectedShape = selectedShape;
    }

    public List<BaseObject> getRotatingPolygons() {
        return rotatingPolygons;
    }

    public BaseObject getRotatingRectangle() {
        return rotatingRectangle;
    }
}
