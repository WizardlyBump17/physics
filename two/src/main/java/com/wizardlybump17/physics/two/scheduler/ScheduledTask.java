package com.wizardlybump17.physics.two.scheduler;

public interface ScheduledTask extends Runnable {

    default boolean reschedule() {
        return false;
    }
}
