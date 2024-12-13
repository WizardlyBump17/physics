package com.wizardlybump17.physics.graphics.two;

import com.wizardlybump17.physics.graphics.two.frame.MainFrame;
import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.graphics.two.renderer.shape.CircleRenderer;
import com.wizardlybump17.physics.graphics.two.renderer.shape.RectangleRenderer;
import com.wizardlybump17.physics.two.Constants;
import com.wizardlybump17.physics.two.Engine;
import com.wizardlybump17.physics.two.container.BaseObjectContainer;
import com.wizardlybump17.physics.two.container.BasicBaseObjectContainer;
import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.physics.object.PhysicsObject;
import com.wizardlybump17.physics.two.position.Vector2D;
import com.wizardlybump17.physics.two.scheduler.Scheduler;
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
        BaseObjectContainer objectContainer = new BasicBaseObjectContainer();
        Scheduler scheduler = new Scheduler();

        Engine.setScheduler(scheduler);

        ObjectsPanel objectsPanel = frame.getObjectsPanel();
        objectsPanel.setObjectContainer(objectContainer);

        int objectCount = 0;
        for (int i = 0; i < 10; i++) {
            objectsPanel.addObject(
                    new PhysicsObject(
                            objectContainer,
                            objectCount++,
                            new Rectangle(
                                    Vector2D.randomVector(current, 0, 0, size.getWidth(), size.getHeight()),
                                    current.nextDouble(90) + 10,
                                    current.nextDouble(90) + 10
                            )
                    ),
                    new RectangleRenderer()
            );

            objectsPanel.addObject(
                    new PhysicsObject(
                            objectContainer,
                            objectCount++,
                            new Circle(
                                    Vector2D.randomVector(current, 0, 0, size.getWidth(), size.getHeight()),
                                    current.nextDouble(50) + 10
                            )
                    ),
                    new CircleRenderer()
            );
        }

            PhysicsObject object = new PhysicsObject(
                    objectContainer,
                    objectCount++,
                    new Circle(
                            Vector2D.randomVector(current, 0, 0, size.getWidth(), size.getHeight()),
                            current.nextDouble(50) + 10
                    )
            );
            objectsPanel.addObject(
                    object,
                    new CircleRenderer()
            );


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                object.getPhysics().setAcceleration(Physics.GRAVITY_VECTOR);

                scheduler.run();
                objectContainer.run();
                frame.repaint();
            }
        }, 0, 1000 / Constants.TICKS_PER_SECOND);
    }
}
