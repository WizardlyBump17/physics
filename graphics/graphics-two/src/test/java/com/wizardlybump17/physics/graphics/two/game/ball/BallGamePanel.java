package com.wizardlybump17.physics.graphics.two.game.ball;

import com.wizardlybump17.physics.two.object.BaseObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Shape;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class BallGamePanel extends JPanel {

    private final @NotNull BallContainer container;

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
                default -> System.err.println("Unknown shape: " + shape.getClass().getName());
            }
        }
    }

    public @NotNull BallContainer getContainer() {
        return container;
    }
}
