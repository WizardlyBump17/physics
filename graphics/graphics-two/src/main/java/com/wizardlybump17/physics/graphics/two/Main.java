package com.wizardlybump17.physics.graphics.two;

import com.wizardlybump17.physics.graphics.two.frame.MainFrame;
import com.wizardlybump17.physics.graphics.two.panel.shape.ShapesPanel;
import com.wizardlybump17.physics.graphics.two.renderer.shape.CircleRenderer;
import com.wizardlybump17.physics.graphics.two.renderer.shape.RectangleRenderer;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        MainFrame frame = new MainFrame("2D test");
        frame.setVisible(true);

        Dimension size = frame.getSize();
        ThreadLocalRandom current = ThreadLocalRandom.current();

        ShapesPanel shapesPanel = frame.getShapesPanel();
        for (int i = 0; i < 10; i++) {
            shapesPanel.addShape(
                    new Rectangle(
                            Vector2D.randomVector(current, 0, 0, size.getWidth(), size.getHeight()),
                            current.nextDouble(90) + 10,
                            current.nextDouble(90) + 10
                    ),
                    new RectangleRenderer()
            );

            shapesPanel.addShape(
                    new Circle(
                            Vector2D.randomVector(current, 0, 0, size.getWidth(), size.getHeight()),
                            current.nextDouble(50) + 10
                    ),
                    new CircleRenderer()
            );
        }
    }
}
