package com.wizardlybump17.physics.two.task.factory;

import com.wizardlybump17.physics.two.task.RunningTask;
import org.jetbrains.annotations.NotNull;

public abstract class RunningTaskFactory {

    public abstract @NotNull RunningTask create(long id, @NotNull Runnable task, long delay, long period, long startedAt);

    public @NotNull RunningTask create(long id, @NotNull Runnable task, long startedAt) {
        return create(id, task, 0, 0, startedAt);
    }

    public @NotNull RunningTask create(long id, @NotNull Runnable task, long delay, long startedAt) {
        return create(id, task, delay, 0, startedAt);
    }
}
