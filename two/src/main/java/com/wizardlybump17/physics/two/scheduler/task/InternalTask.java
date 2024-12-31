package com.wizardlybump17.physics.two.scheduler.task;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class InternalTask {

    private final long id;
    private boolean running = true;
    private final long startedAtTick;
    private final long delay;
    private final long period;
    private final @NotNull Runnable runnable;

    public InternalTask(long id, long startedAtTick, long delay, long period, @NotNull Runnable runnable) {
        this.id = id;
        this.startedAtTick = startedAtTick;
        this.delay = delay;
        this.period = period;
        this.runnable = runnable;
    }

    public long getId() {
        return id;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long getStartedAtTick() {
        return startedAtTick;
    }

    public long getDelay() {
        return delay;
    }

    public long getPeriod() {
        return period;
    }

    public @NotNull Runnable getRunnable() {
        return runnable;
    }

    public boolean isTimeToRun(long currentTick) {
        return currentTick % startedAtTick + delay == 0;
    }
}
