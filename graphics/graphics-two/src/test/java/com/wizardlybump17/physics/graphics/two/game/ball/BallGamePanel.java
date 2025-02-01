package com.wizardlybump17.physics.graphics.two.game.ball;

import com.wizardlybump17.physics.two.Constants;
import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class BallGamePanel extends JPanel {

    private final @NotNull BallContainer container;
    private final @NotNull Map<Class<?>, Integer> unknownShapeDelay = new HashMap<>();

    public BallGamePanel(@NotNull BallContainer container) {
        this.container = container;
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
                    double width = rectangle.getWidth();
                    double height = rectangle.getHeight();

                    graphics.setColor(Color.BLUE);
                    graphics.fillRect((int) position.x(), (int) position.y(), (int) width, (int) height);
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
