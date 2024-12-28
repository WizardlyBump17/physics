package com.wizardlybump17.physics.two.scheduler.task;

public interface Task extends Runnable {

    default boolean reschedule() {
        return false;
    }
}
