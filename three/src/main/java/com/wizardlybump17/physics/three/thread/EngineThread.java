package com.wizardlybump17.physics.three.thread;

import com.wizardlybump17.physics.Constants;
import com.wizardlybump17.physics.task.scheduler.TaskScheduler;
import com.wizardlybump17.physics.three.registry.BaseObjectContainerRegistry;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;

public class EngineThread extends Thread {

    private static final @NotNull AtomicInteger THREAD_COUNTER = new AtomicInteger();

    private final @NotNull TaskScheduler scheduler;
    private final @NotNull BaseObjectContainerRegistry containerRegistry;
    private volatile boolean running = true;

    public EngineThread(@NotNull TaskScheduler scheduler, @NotNull BaseObjectContainerRegistry containerRegistry) {
        super("EngineThread-" + THREAD_COUNTER.getAndIncrement());
        this.scheduler = scheduler;
        this.containerRegistry = containerRegistry;
    }

    public @NotNull TaskScheduler getScheduler() {
        return scheduler;
    }

    public @NotNull BaseObjectContainerRegistry getContainerRegistry() {
        return containerRegistry;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        while (running) {
            tickScheduler();
            tickContainers();

            try {
                Thread.sleep(Duration.ofNanos((long) Constants.NANOS_PER_TICK));
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
