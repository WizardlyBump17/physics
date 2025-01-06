package com.wizardlybump17.physics.two.thread;

import com.wizardlybump17.physics.two.Constants;
import com.wizardlybump17.physics.two.registry.BaseObjectContainerRegistry;
import com.wizardlybump17.physics.two.task.scheduler.TaskScheduler;
import org.jetbrains.annotations.NotNull;

public class EngineThread extends Thread {

    private final @NotNull TaskScheduler scheduler;
    private final @NotNull BaseObjectContainerRegistry containerRegistry;
    private boolean running = true;

    public EngineThread(@NotNull TaskScheduler scheduler, @NotNull BaseObjectContainerRegistry containerRegistry) {
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
        long start = System.currentTimeMillis();
        long ticks = 0;
        while (running) {
            ticks++;

            tickScheduler();
            tickContainers();

            while (System.currentTimeMillis() < start + ticks * Constants.MILLIS_PER_TICK) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
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
}
