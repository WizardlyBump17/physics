package com.wizardlybump17.physics.graphics.two.panel.object;

import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelKeyboardListener;
import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseListener;
import com.wizardlybump17.physics.graphics.two.listener.panel.shape.ShapePanelMouseMotionListener;
import com.wizardlybump17.physics.graphics.two.renderer.shape.CircleRenderer;
import com.wizardlybump17.physics.graphics.two.renderer.shape.RectangleRenderer;
import com.wizardlybump17.physics.graphics.two.renderer.shape.RotatingPolygonRenderer;
import com.wizardlybump17.physics.graphics.two.renderer.shape.ShapeRenderer;
import com.wizardlybump17.physics.two.container.BaseObjectContainer;
import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import com.wizardlybump17.physics.two.shape.Shape;
import com.wizardlybump17.physics.two.shape.rotating.RotatingPolygon;
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
    private final transient List<BaseObject> rotatingPolygons = new ArrayList<>();
    private transient PanelObject closestObjectToMouse;
    private transient Vector2D closestPointToMouse;
    private transient Vector2D mouseLocation;

    private transient final ShapePanelMouseListener mouseListener = new ShapePanelMouseListener(this);
    private transient final ShapePanelMouseMotionListener mouseMotionListener = new ShapePanelMouseMotionListener(this);
    private transient final ShapePanelKeyboardListener keyboardListener = new ShapePanelKeyboardListener(this);

    public ObjectsPanel() {
        setFocusable(true);
        requestFocus();

        addMouseListener(mouseListener);
        addMouseMotionListener(mouseMotionListener);
        addKeyListener(keyboardListener);
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

            rotatingPolygons.clear();
            {
                for (int i = 0; i < 2; i++) {
                    Vector2D center = Vector2D.randomVector(random, 0, 0, size.getWidth(), size.getHeight());

                    List<Vector2D> points = new ArrayList<>();
                    for (int j = 0; j < 10; j++) {
                        points.add(new Vector2D(random.nextDouble(-200, 200), random.nextDouble(-200, 200)));
                    }

                    BaseObject rotatingPolygon = new PhysicsObject(
                            objectContainer,
                            objectCount++,
                            new RotatingPolygon(
                                    center,
                                    points,
                                    0
                            )
                    );
                    addObject(rotatingPolygon, new RotatingPolygonRenderer());
                    rotatingPolygons.add(rotatingPolygon);
                }

                for (int i = 0; i < 2; i++) {
                    Vector2D center = Vector2D.randomVector(random, 0, 0, size.getWidth(), size.getHeight());
                    double polySize = 10;
                    BaseObject rotatingPolygon = new PhysicsObject(
                            objectContainer,
                            objectCount++,
                            new RotatingPolygon(
                                    center,
                                    new ArrayList<>(List.of(
                                            new Vector2D(-polySize, polySize),
                                            new Vector2D(polySize, polySize),
                                            new Vector2D(polySize, -polySize),
                                            new Vector2D(-polySize, -polySize)
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

            shapes.values().forEach(panelShape -> ((ShapeRenderer<Shape>) panelShape.getRenderer()).render(graphics, panelShape.getShape()));
            if (selectedShape != null)
                ((ShapeRenderer<Shape>) selectedShape.getRenderer()).render(graphics, selectedShape.getShape());

            for (BaseObject rotatingPolygon : rotatingPolygons)
                shapes.get(rotatingPolygon.getId()).setHasCollisions(false);

            drawClosest(graphics);
        }
    }

    protected void drawClosest(@NotNull Graphics graphics) {
        if (closestObjectToMouse == null || closestPointToMouse == null || mouseLocation == null)
            return;

        graphics.setColor(Color.BLACK);
        graphics.drawLine(
                (int) mouseLocation.x(), (int) mouseLocation.y(),
                (int) closestPointToMouse.x(), (int) closestPointToMouse.y()
        );
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

    public PanelObject getClosestObjectToMouse() {
        return closestObjectToMouse;
    }

    public void setClosestObjectToMouse(PanelObject closestObjectToMouse) {
        this.closestObjectToMouse = closestObjectToMouse;
    }

    public Vector2D getClosestPointToMouse() {
        return closestPointToMouse;
    }

    public void setClosestPointToMouse(Vector2D closestPointToMouse) {
        this.closestPointToMouse = closestPointToMouse;
    }

    public Vector2D getMouseLocation() {
        return mouseLocation;
    }

    public void setMouseLocation(Vector2D mouseLocation) {
        this.mouseLocation = mouseLocation;
    }

    public ShapePanelMouseListener getMouseListener() {
        return mouseListener;
    }

    public ShapePanelMouseMotionListener getMouseMotionListener() {
        return mouseMotionListener;
    }

    public ShapePanelKeyboardListener getKeyboardListener() {
        return keyboardListener;
    }
}
