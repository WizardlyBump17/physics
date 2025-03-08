package com.wizardlybump17.physics.task.registered;

import com.wizardlybump17.physics.util.AtomicUtil;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.atomic.AtomicReference;

@ApiStatus.Internal
public class RegisteredTaskImpl implements RegisteredTask {

    public static final int NO_REPEATING = -1;
    public static final int CANCELED = -1;
    public static final int INTERNAL_ID = -1;

    private final int id;
    private final long startedAt;
    private final long delay;
    private final long period;
    private final @NotNull Runnable runnable;
    private final @NotNull AtomicReference<RegisteredTaskImpl> nextTask = new AtomicReference<>();
    private long nextRun;

    public RegisteredTaskImpl(int id, long startedAt, long delay, long period, @NotNull Runnable runnable) {
        this.id = id;
        this.startedAt = startedAt;
        this.delay = Math.clamp(delay, 0, Long.MAX_VALUE);
        this.period = Math.clamp(period, NO_REPEATING, Long.MAX_VALUE);
        this.runnable = runnable;
        nextRun = startedAt + delay;
    }

    public RegisteredTaskImpl() {
        this(INTERNAL_ID, 0, 0, NO_REPEATING, () -> {});
    }

    @Override
    public int getId() {
        return id;
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

    @Override
    public @NotNull Runnable getRunnable() {
        return runnable;
    }

    public void run() {
        runnable.run();
    }

    public boolean isRepeatable() {
        return period != NO_REPEATING;
    }

    @Override
    public boolean isCancelled() {
        return nextRun == CANCELED;
    }

    @Override
    public void cancel() {
        nextRun = CANCELED;
    }

    public void setNextTask(@Nullable RegisteredTaskImpl nextTask) {
        AtomicUtil.set(this.nextTask, nextTask);
    }

    public @Nullable RegisteredTaskImpl getNextTask() {
        return nextTask.get();
    }

    public long getNextRun() {
        return nextRun;
    }

    public void setNextRun(long nextRun) {
        this.nextRun = nextRun;
    }

    public boolean isInternal() {
        return id == INTERNAL_ID;
    }
}
