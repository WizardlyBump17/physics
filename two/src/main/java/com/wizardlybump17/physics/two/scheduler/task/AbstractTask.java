package com.wizardlybump17.physics.two.scheduler.task;

import com.wizardlybump17.physics.two.Engine;
import com.wizardlybump17.physics.two.scheduler.Scheduler;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTask implements Runnable {

    private RunningTask task;

    public void schedule() {
        schedule(Engine.getScheduler());
    }

    public void schedule(long delay) {
        schedule(Engine.getScheduler(), delay);
    }

    public void schedule(long delay, long period) {
        schedule(Engine.getScheduler(), delay, period);
    }

    public void schedule(@NotNull Scheduler scheduler) {
        assertNotScheduled();
        task = scheduler.schedule(this);
    }

    public void schedule(@NotNull Scheduler scheduler, long delay) {
        assertNotScheduled();
        task = scheduler.schedule(this, delay);
    }

    public void schedule(@NotNull Scheduler scheduler, long delay, long period) {
        assertNotScheduled();
        task = scheduler.schedule(this, delay, period);
    }

    private void assertScheduled() {
        if (task == null)
            throw new IllegalStateException("The task is not scheduled yet.");
    }

    private void assertNotScheduled() {
        if (task != null)
            throw new IllegalStateException("The task is already scheduled as " + task.getId() + ".");
    }

    public void cancel(@NotNull Scheduler scheduler) {
        assertScheduled();
        scheduler.cancelTask(task.getId());
    }

    public void cancel() {
        cancel(Engine.getScheduler());
    }
}
