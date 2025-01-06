package com.wizardlybump17.physics.two.task.factory;

import com.wizardlybump17.physics.two.task.RegisteredTask;
import org.jetbrains.annotations.NotNull;

public class RegisteredTaskFactory {

    public @NotNull RegisteredTask create(long id, @NotNull Runnable task, long delay, long period, long startedAt) {
        return new RegisteredTask(id, startedAt, delay, period, task);
    }

    public @NotNull RegisteredTask create(long id, @NotNull Runnable task, long startedAt) {
        return create(id, task, 0, 0, startedAt);
    }

    public @NotNull RegisteredTask create(long id, @NotNull Runnable task, long delay, long startedAt) {
        return create(id, task, delay, 0, startedAt);
    }
}
