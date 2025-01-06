package com.wizardlybump17.physics.graphics.two;

import com.wizardlybump17.physics.graphics.two.frame.MainFrame;
import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.two.Constants;
import com.wizardlybump17.physics.two.Engine;
import com.wizardlybump17.physics.two.container.BaseObjectContainer;
import com.wizardlybump17.physics.two.container.BasicBaseObjectContainer;
import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.task.factory.BasicTaskFactory;
import com.wizardlybump17.physics.two.task.scheduler.TaskScheduler;
import com.wizardlybump17.physics.two.thread.EngineThread;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Main {

    public static void main(String[] args) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println(dateFormat.format(System.currentTimeMillis()));

        MainFrame frame = new MainFrame("2D test");
        frame.setVisible(true);

        BaseObjectContainer objectContainer = new BasicBaseObjectContainer();
        TaskScheduler scheduler = new TaskScheduler(new BasicTaskFactory());

        Engine.setScheduler(scheduler);

        ObjectsPanel objectsPanel = frame.getObjectsPanel();
        objectsPanel.setObjectContainer(objectContainer);
        objectsPanel.regenerate();

        scheduler.schedule(
                () -> {
                    System.out.println("Hi! " + dateFormat.format(System.currentTimeMillis()));
                    try {
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                },
                Constants.TICKS_PER_SECOND * 10 //10 seconds
        );
        scheduler.schedule(
                () -> {
                    objectsPanel.getFallingBall().getPhysics().setAcceleration(Physics.GRAVITY_VECTOR); //TODO: move to the thread
                    objectContainer.run();
                    frame.repaint();
                },
                0,
                1
        );
        scheduler.schedule(
                () -> System.out.println(scheduler.getCurrentTick()),
                0,
                Constants.TICKS_PER_SECOND
        );

        new EngineThread(scheduler).start();
    }
}
