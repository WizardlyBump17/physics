package com.wizardlybump17.physics.three.thread;

import com.wizardlybump17.physics.task.scheduler.TaskScheduler;
import com.wizardlybump17.physics.three.registry.ShapesGroupsContainerRegistry;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class EngineThread extends Thread {

    private static final @NotNull AtomicInteger THREAD_COUNTER = new AtomicInteger();

    private final @NotNull TaskScheduler scheduler;
    private final @NotNull ShapesGroupsContainerRegistry containerRegistry;
    private volatile boolean running = true;
    private volatile long ticksPerSecond;
    private volatile long nanosPerTick;

    public EngineThread(@NotNull TaskScheduler scheduler, @NotNull ShapesGroupsContainerRegistry containerRegistry, long ticksPerSecond) {
        super("EngineThread-" + THREAD_COUNTER.getAndIncrement());
        this.scheduler = scheduler;
        this.containerRegistry = containerRegistry;
        setTicksPerSecond(ticksPerSecond);
    }

    public @NotNull TaskScheduler getScheduler() {
        return scheduler;
    }

    public @NotNull ShapesGroupsContainerRegistry getContainerRegistry() {
        return containerRegistry;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public long getTicksPerSecond() {
        return ticksPerSecond;
    }

    public void setTicksPerSecond(long ticksPerSecond) {
        if (ticksPerSecond < 0)
            throw new IllegalArgumentException("The ticks per second can not be negative.");

        this.ticksPerSecond = ticksPerSecond;
        nanosPerTick = 1_000_000_000 / ticksPerSecond;
    }

    public long getNanosPerTick() {
        return nanosPerTick;
    }

    @Override
    public void run() {
        while (running) {
            tickScheduler();
            tickContainers();

            try {
                Thread.sleep(Duration.ofNanos(nanosPerTick));
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
    }

    protected void tickScheduler() {
        scheduler.tick();
    }

    protected void tickContainers() {
        containerRegistry.forEach((id, container) -> {
            container.tick();
        });
    }

    public static void catchAsync(@NotNull String operation) {
        if (!isMainThread())
            throw new IllegalStateException("Can not run asynchronously: " + operation);
    }

    public static void catchAsync() {
        if (!isMainThread())
            throw new IllegalStateException("Can not run asynchronously");
    }

    public static boolean isMainThread() {
        return currentThread() instanceof EngineThread;
    }
}
