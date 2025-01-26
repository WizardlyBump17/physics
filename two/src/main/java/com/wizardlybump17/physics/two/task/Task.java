package com.wizardlybump17.physics.two.task;

import com.wizardlybump17.physics.two.Engine;
import com.wizardlybump17.physics.two.task.scheduler.TaskScheduler;
import org.jetbrains.annotations.NotNull;

public abstract class Task implements Runnable {

    public static final int NO_REPEATING = -1;

    private RegisteredTask registeredTask;

    public void schedule() {
        schedule(Engine.getScheduler());
    }

    public void schedule(long delay) {
        schedule(Engine.getScheduler(), delay);
    }

    public void schedule(long delay, long period) {
        schedule(Engine.getScheduler(), delay, period);
    }

    public void schedule(@NotNull TaskScheduler scheduler) {
        assertNotScheduled();
        registeredTask = scheduler.schedule(this);
    }

    public void schedule(@NotNull TaskScheduler scheduler, long delay) {
        assertNotScheduled();
        registeredTask = scheduler.schedule(this, delay);
    }

    public void schedule(@NotNull TaskScheduler scheduler, long delay, long period) {
        assertNotScheduled();
        registeredTask = scheduler.schedule(this, delay, period);
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
}
