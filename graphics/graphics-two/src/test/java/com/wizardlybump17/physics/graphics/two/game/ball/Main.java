package com.wizardlybump17.physics.graphics.two.game.ball;

import com.wizardlybump17.physics.two.Engine;
import com.wizardlybump17.physics.two.registry.BaseObjectContainerRegistry;
import com.wizardlybump17.physics.two.task.factory.RegisteredTaskFactory;
import com.wizardlybump17.physics.two.task.scheduler.TaskScheduler;
import com.wizardlybump17.physics.two.thread.EngineThread;

public class Main {

    public static void main(String[] args) {
        BaseObjectContainerRegistry containerRegistry = new BaseObjectContainerRegistry();
        TaskScheduler scheduler = new TaskScheduler(new RegisteredTaskFactory());
        EngineThread thread = new EngineThread(scheduler, containerRegistry);

        Engine.setObjectContainerRegistry(containerRegistry);
        Engine.setScheduler(scheduler);
        Engine.setThread(thread);

        thread.start();

        BallGame game = new BallGame();
        game.init();
        game.getMainFrame().setVisible(true);

        scheduler.schedule(
                () -> game.getMainPanel().repaint(),
                0,
                1
        );
    }
}
