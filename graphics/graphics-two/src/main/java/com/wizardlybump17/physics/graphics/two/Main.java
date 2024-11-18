package com.wizardlybump17.physics.graphics.two;

import com.wizardlybump17.physics.graphics.two.frame.MainFrame;
import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.graphics.two.renderer.shape.CircleRenderer;
import com.wizardlybump17.physics.graphics.two.renderer.shape.RectangleRenderer;
import com.wizardlybump17.physics.two.Constants;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.shape.Circle;
import com.wizardlybump17.physics.two.shape.Rectangle;

import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static void main(String[] args) {
        MainFrame frame = new MainFrame("2D test");
        frame.setVisible(true);

        Dimension size = frame.getSize();
        ThreadLocalRandom current = ThreadLocalRandom.current();

        ObjectsPanel objectsPanel = frame.getObjectsPanel();
        int objectCount = 0;
        for (int i = 0; i < 10; i++) {
            objectsPanel.addShape(
                    new PhysicsObject(
                            objectCount++,
                            new Rectangle(
                                    Vector2D.randomVector(current, 0, 0, size.getWidth(), size.getHeight()),
                                    current.nextDouble(90) + 10,
                                    current.nextDouble(90) + 10
                            )
                    ),
                    new RectangleRenderer()
            );

            objectsPanel.addShape(
                    new PhysicsObject(
                            objectCount++,
                            new Circle(
                                    Vector2D.randomVector(current, 0, 0, size.getWidth(), size.getHeight()),
                                    current.nextDouble(50) + 10
                            )
                    ),
                    new CircleRenderer()
            );
        }

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                frame.tick();
            }
        }, 1000 / Constants.TICKS_PER_SECOND);
    }
}
