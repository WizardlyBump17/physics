package com.wizardlybump17.physics.two.scheduler.task;

public abstract class AbstractTask {

    private Long id;

    public abstract void run();

    private void assertScheduled() {
        if (id == null)
            throw new IllegalStateException("The task is not scheduled yet.");
    }

    private void assertNotScheduled() {
        if (id != null)
            throw new IllegalStateException("The task is already scheduled as " + id + ".");
    }
}
