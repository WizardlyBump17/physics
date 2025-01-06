package com.wizardlybump17.physics.two.task;

public interface RunningTask extends Runnable {

    long getId();

    boolean isRunning();

    void setRunning(boolean running);

    long getDelay();

    long getPeriod();

    long getStartedAt();

    default boolean isTimeToRun(long currentTick) {
        if (getDelay() == 0 && getPeriod() == 0 && currentTick > getStartedAt())
            return true;
        long period = getStartedAt() + getDelay() + getPeriod() - 1;
        return period == 0 || currentTick % period == 0;
    }

    default boolean isRepeatable() {
        return getPeriod() > 0;
    }
}
