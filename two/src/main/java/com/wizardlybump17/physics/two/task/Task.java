package com.wizardlybump17.physics.two.task;

import com.wizardlybump17.physics.two.Engine;
import com.wizardlybump17.physics.two.task.registered.RegisteredTask;
import com.wizardlybump17.physics.two.task.scheduler.TaskScheduler;
import org.jetbrains.annotations.NotNull;

public abstract class Task {

    private RegisteredTask registeredTask;

    public abstract void run();

    public @NotNull RegisteredTask schedule() {
        return schedule(Engine.getScheduler());
    }

    public @NotNull RegisteredTask schedule(long delay) {
        return schedule(Engine.getScheduler(), delay);
    }

    public @NotNull RegisteredTask schedule(long delay, long period) {
        return schedule(Engine.getScheduler(), delay, period);
    }

    public @NotNull RegisteredTask schedule(@NotNull TaskScheduler scheduler) {
        assertNotScheduled();
        return registeredTask = scheduler.schedule(this::run);
    }

    public @NotNull RegisteredTask schedule(@NotNull TaskScheduler scheduler, long delay) {
        assertNotScheduled();
        return registeredTask = scheduler.schedule(this::run, delay);
    }

    public @NotNull RegisteredTask schedule(@NotNull TaskScheduler scheduler, long delay, long period) {
        assertNotScheduled();
        return registeredTask = scheduler.schedule(this::run, delay, period);
    }

    private void assertScheduled() {
        if (registeredTask == null)
            throw new IllegalStateException("The task is not scheduled yet.");
    }

    private void assertNotScheduled() {
        if (registeredTask != null)
            throw new IllegalStateException("The task is already scheduled as " + registeredTask.getId() + ".");
    }

    public void cancel(@NotNull TaskScheduler scheduler) {
        assertScheduled();
        scheduler.cancelTask(registeredTask.getId());
    }

    public void cancel() {
        cancel(Engine.getScheduler());
    }

    public boolean isScheduled() {
        return registeredTask != null && !registeredTask.isCancelled();
    }
}
