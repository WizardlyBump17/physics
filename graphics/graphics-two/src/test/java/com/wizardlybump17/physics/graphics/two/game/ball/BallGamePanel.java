package com.wizardlybump17.physics.graphics.two.game.ball;

import com.wizardlybump17.physics.graphics.two.game.ball.platform.Platform;
import com.wizardlybump17.physics.graphics.two.game.ball.platform.PlatformPhysics;
import com.wizardlybump17.physics.two.Constants;
import com.wizardlybump17.physics.two.Engine;
import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class BallGamePanel extends JPanel {

    private final @NotNull BallContainer container;
    private final @NotNull Map<Class<?>, Integer> unknownShapeDelay = new HashMap<>();

    public BallGamePanel(@NotNull BallContainer container) {
        this.container = container;
        init();
    }

    protected void init() {
        setFocusable(true);
        requestFocus();

        initListeners();
    }

    protected void initListeners() {
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(@NotNull KeyEvent event) {
                Engine.getScheduler().schedule(() -> {
                    Platform platform = container.getPlatform();
                    PlatformPhysics physics = platform.getPhysics();

                    switch (event.getKeyCode()) {
                        case KeyEvent.VK_A -> physics.moveLeft();
                        case KeyEvent.VK_D -> physics.moveRight();
                    }
                });
            }

            @Override
            public void keyReleased(@NotNull KeyEvent event) {
                Engine.getScheduler().schedule(() -> {
                    Platform platform = container.getPlatform();
                    PlatformPhysics physics = platform.getPhysics();

                    switch (event.getKeyCode()) {
                        case KeyEvent.VK_A -> {
                            if (physics.getDirection() == PlatformPhysics.Direction.LEFT)
                                physics.stopMovement();
                        }
                        case KeyEvent.VK_D -> {
                            if (physics.getDirection() == PlatformPhysics.Direction.RIGHT)
                                physics.stopMovement();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void paint(@NotNull Graphics graphics) {
        Toolkit.getDefaultToolkit().sync();

        super.paintComponent(graphics);

        for (BaseObject object : container.getObjects()) {
            Shape shape = object.getShape();
            Vector2D position = object.getPosition();

            switch (shape) {
                case Circle circle -> {
                    double radius = circle.getRadius();
                    double x = position.x() - radius;
                    double y = position.y() - radius;
                    double diameter = radius * 2;

                    graphics.setColor(Color.RED);
                    graphics.fillOval((int) x, (int) y, (int) diameter, (int) diameter);
                }
                case Rectangle rectangle -> {
                    Vector2D min = rectangle.getMin();
                    double width = rectangle.getWidth();
                    double height = rectangle.getHeight();

                    graphics.setColor(Color.BLUE);
                    graphics.fillRect((int) min.x(), (int) min.y(), (int) width, (int) height);
                }
                default -> {
                    Class<? extends Shape> shapeClass = shape.getClass();
                    int delay = unknownShapeDelay.computeIfAbsent(shapeClass, $ -> 0);
                    unknownShapeDelay.put(shapeClass, delay + 1);
                    if (delay % Constants.TICKS_PER_SECOND == 0)
                        System.err.println("We do not know how to draw a " + shapeClass.getName());
                }
            }
        }
    }

    public @NotNull BallContainer getContainer() {
        return container;
    }
}
