package com.wizardlybump17.physics.two.task.factory;

import com.wizardlybump17.physics.two.task.BasicTask;
import com.wizardlybump17.physics.two.task.RunningTask;
import org.jetbrains.annotations.NotNull;

public class BasicTaskFactory extends RunningTaskFactory {

    @Override
    public @NotNull RunningTask create(long id, @NotNull Runnable task, long delay, long period, long startedAt) {
        return new BasicTask(id, startedAt, delay, period, task);
    }
}
