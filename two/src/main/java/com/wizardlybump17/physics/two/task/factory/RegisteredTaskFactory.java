package com.wizardlybump17.physics.two.task.factory;

import com.wizardlybump17.physics.two.task.RegisteredTask;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class RegisteredTaskFactory {

    public @NotNull RegisteredTask create(int id, @NotNull Runnable task, long delay, long period, long startedAt) {
        return new RegisteredTask(id, startedAt, delay, period, task);
    }

    public @NotNull RegisteredTask create(int id, @NotNull Runnable task, long delay, long startedAt) {
        return create(id, task, delay, RegisteredTask.NO_REPEATING, startedAt);
    }

    public @NotNull RegisteredTask create(int id, @NotNull Runnable task, long startedAt) {
        return create(id, task, 0, RegisteredTask.NO_REPEATING, startedAt);
    }
}
