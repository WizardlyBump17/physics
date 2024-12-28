package com.wizardlybump17.physics.graphics.two;

import com.wizardlybump17.physics.graphics.two.frame.MainFrame;
import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.two.Constants;
import com.wizardlybump17.physics.two.Engine;
import com.wizardlybump17.physics.two.container.BaseObjectContainer;
import com.wizardlybump17.physics.two.container.BasicBaseObjectContainer;
import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.scheduler.Scheduler;
import com.wizardlybump17.physics.two.scheduler.task.Task;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        System.out.println(dateFormat.format(System.currentTimeMillis()));

        MainFrame frame = new MainFrame("2D test");
        frame.setVisible(true);

        BaseObjectContainer objectContainer = new BasicBaseObjectContainer();
        Scheduler scheduler = new Scheduler();

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
                new Task() {
                    @Override
                    public void run() {
                        objectsPanel.getFallingBall().getPhysics().setAcceleration(Physics.GRAVITY_VECTOR);
                        objectContainer.run();
                        frame.repaint();
                    }

                    @Override
                    public boolean reschedule() {
                        return true;
                    }
                }
        );
        scheduler.schedule(
                () -> System.out.println(scheduler.getCurrentTick()),
                0,
                17
        );

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                scheduler.run();
            }
        }, 0, 1000 / Constants.TICKS_PER_SECOND);
    }
}
