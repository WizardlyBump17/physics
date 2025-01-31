package com.wizardlybump17.physics.two.task.factory;

import com.wizardlybump17.physics.two.task.registered.RegisteredTaskImpl;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class RegisteredTaskFactory {

    public @NotNull RegisteredTaskImpl create(int id, @NotNull Runnable task, long delay, long period, long startedAt) {
        return new RegisteredTaskImpl(id, startedAt, delay, period, task);
    }

    public @NotNull RegisteredTaskImpl create(int id, @NotNull Runnable task, long delay, long startedAt) {
        return create(id, task, delay, RegisteredTaskImpl.NO_REPEATING, startedAt);
    }

    public @NotNull RegisteredTaskImpl create(int id, @NotNull Runnable task, long startedAt) {
        return create(id, task, 0, RegisteredTaskImpl.NO_REPEATING, startedAt);
    }
}
