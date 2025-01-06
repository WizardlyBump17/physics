package com.wizardlybump17.physics.two.task;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class RegisteredTask {

    private final long id;
    private boolean running = true;
    private final long startedAt;
    private final long delay;
    private final long period;
    private final @NotNull Runnable runnable;

    public RegisteredTask(long id, long startedAt, long delay, long period, @NotNull Runnable runnable) {
        this.id = id;
        this.startedAt = startedAt;
        this.delay = Math.clamp(delay, 0, Long.MAX_VALUE);
        this.period = Math.clamp(period, 0, Long.MAX_VALUE);
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

    public long getStartedAt() {
        return startedAt;
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

    public void run() {
        runnable.run();
    }

    public boolean isTimeToRun(long currentTick) {
        if (getDelay() == 0 && getPeriod() == 0 && currentTick > getStartedAt())
            return true;
        long period = getStartedAt() + getDelay() + getPeriod() - 1;
        return period == 0 || currentTick % period == 0;
    }

    public boolean isRepeatable() {
        return getPeriod() > 0;
    }
}
