package com.wizardlybump17.physics.two.task;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Internal
public class BasicTask implements RunningTask {

    private final long id;
    private boolean running = true;
    private final long startedAt;
    private final long delay;
    private final long period;
    private final @NotNull Runnable runnable;

    public BasicTask(long id, long startedAt, long delay, long period, @NotNull Runnable runnable) {
        this.id = id;
        this.startedAt = startedAt;
        this.delay = Math.clamp(delay, 0, Long.MAX_VALUE);
        this.period = Math.clamp(period, 0, Long.MAX_VALUE);
        this.runnable = runnable;
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public long getStartedAt() {
        return startedAt;
    }

    @Override
    public long getDelay() {
        return delay;
    }

    @Override
    public long getPeriod() {
        return period;
    }

    public @NotNull Runnable getRunnable() {
        return runnable;
    }

    @Override
    public void run() {
        runnable.run();
    }
}
