package com.wizardlybump17.physics.two.task;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Comparator;

@ApiStatus.Internal
public class RegisteredTask implements Comparable<RegisteredTask> {

    public static final @NotNull Comparator<RegisteredTask> COMPARATOR = Comparator
            .comparing(RegisteredTask::getNextRun)
            .thenComparing(RegisteredTask::getStartedAt);
    public static final int NO_REPEATING = -1;

    private final int id;
    private boolean running = true;
    private final long startedAt;
    private final long delay;
    private final long period;
    private final @NotNull Runnable runnable;
    private RegisteredTask nextTask;
    private long nextRun;

    public RegisteredTask(int id, long startedAt, long delay, long period, @NotNull Runnable runnable) {
        this.id = id;
        this.startedAt = startedAt;
        this.delay = Math.clamp(delay, 0, Long.MAX_VALUE);
        this.period = Math.clamp(period, 0, Long.MAX_VALUE);
        this.runnable = runnable;
        nextRun = startedAt + delay;
    }

    public RegisteredTask() {
        this(-1, 0, 0, 0, () -> {});
    }

    public int getId() {
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

    public boolean isRepeatable() {
        return period != Task.NO_REPEATING;
    }

    public void setNextTask(@Nullable RegisteredTask nextTask) {
        this.nextTask = nextTask;
    }

    public @Nullable RegisteredTask getNextTask() {
        return nextTask;
    }

    @Override
    public int compareTo(@NotNull RegisteredTask other) {
        return COMPARATOR.compare(this, other);
    }

    public long getNextRun() {
        return nextRun;
    }

    public void setNextRun(long nextRun) {
        this.nextRun = nextRun;
    }
}
