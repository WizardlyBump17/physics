package com.wizardlybump17.physics.two.scheduler.task;

public interface RunningTask extends Runnable {

    long getId();

    boolean isRunning();

    void setRunning(boolean running);

    long getDelay();

    long getPeriod();

    long getStartedAt();

    default boolean isTimeToRun(long currentTick) {
        return currentTick % getStartedAt() + getDelay() + getPeriod() == 0;
    }
}
