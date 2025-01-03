package com.wizardlybump17.physics.two.scheduler.task;

public interface RunningTask {

    long getId();

    boolean isRunning();

    void setRunning(boolean running);

    long getDelay();

    long getPeriod();
}
