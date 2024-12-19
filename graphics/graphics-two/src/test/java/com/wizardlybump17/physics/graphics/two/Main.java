package com.wizardlybump17.physics.graphics.two;

import com.wizardlybump17.physics.graphics.two.frame.MainFrame;
import com.wizardlybump17.physics.graphics.two.panel.object.ObjectsPanel;
import com.wizardlybump17.physics.two.Constants;
import com.wizardlybump17.physics.two.Engine;
import com.wizardlybump17.physics.two.container.BaseObjectContainer;
import com.wizardlybump17.physics.two.container.BasicBaseObjectContainer;
import com.wizardlybump17.physics.two.physics.Physics;
import com.wizardlybump17.physics.two.scheduler.Scheduler;

import java.util.Timer;
import java.util.TimerTask;

public class Main {

    public static void main(String[] args) {
        MainFrame frame = new MainFrame("2D test");
        frame.setVisible(true);

        BaseObjectContainer objectContainer = new BasicBaseObjectContainer();
        Scheduler scheduler = new Scheduler();

        Engine.setScheduler(scheduler);

        ObjectsPanel objectsPanel = frame.getObjectsPanel();
        objectsPanel.setObjectContainer(objectContainer);
        objectsPanel.regenerate();


        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                objectsPanel.getFallingBall().getPhysics().setAcceleration(Physics.GRAVITY_VECTOR);

                objectContainer.run();
                scheduler.run();
                frame.repaint();
            }
        }, 0, 1000 / Constants.TICKS_PER_SECOND);
    }
}
