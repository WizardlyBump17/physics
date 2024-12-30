package com.wizardlybump17.physics.two.scheduler.task;

import com.wizardlybump17.physics.two.Engine;

public abstract class AbstractTask {

    private Long id;

    public abstract void run();

    public void schedule() {
        assertNotScheduled();
        id = Engine.getScheduler().schedule(this);
    }

    private void assertScheduled() {
        if (id == null)
            throw new IllegalStateException("The task is not scheduled yet.");
    }

    private void assertNotScheduled() {
        if (id != null)
            throw new IllegalStateException("The task is already scheduled as " + id + ".");
    }
}
