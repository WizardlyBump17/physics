package com.wizardlybump17.physics.two.scheduler.task;

import com.wizardlybump17.physics.two.Engine;
import com.wizardlybump17.physics.two.scheduler.Scheduler;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractTask {

    private RunningTask task;

    public abstract void run();

    public void schedule() {
        schedule(Engine.getScheduler());
    }

    public void schedule(@NotNull Scheduler scheduler) {
        assertNotScheduled();
        task = scheduler.schedule(this);
    }

    private void assertScheduled() {
        if (task == null)
            throw new IllegalStateException("The task is not scheduled yet.");
    }

    private void assertNotScheduled() {
        if (task != null)
            throw new IllegalStateException("The task is already scheduled as " + task.getId() + ".");
    }
}
