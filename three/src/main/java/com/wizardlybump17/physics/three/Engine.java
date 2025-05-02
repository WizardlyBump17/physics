package com.wizardlybump17.physics.three;

import com.wizardlybump17.physics.task.scheduler.TaskScheduler;
import com.wizardlybump17.physics.three.registry.BaseObjectContainerRegistry;
import com.wizardlybump17.physics.three.thread.EngineThread;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class Engine {

    private final @NotNull BaseObjectContainerRegistry objectContainerRegistry;
    private final @NotNull Thread thread;
    private final @NotNull TaskScheduler scheduler;

    public Engine(@NotNull BaseObjectContainerRegistry objectContainerRegistry, @NotNull Thread thread, @NotNull TaskScheduler scheduler) {
        this.objectContainerRegistry = objectContainerRegistry;
        this.thread = thread;
        this.scheduler = scheduler;
    }

    public @NotNull BaseObjectContainerRegistry getObjectContainerRegistry() {
        return objectContainerRegistry;
    }

    public @NotNull Thread getThread() {
        return thread;
    }

    public @NotNull TaskScheduler getScheduler() {
        return scheduler;
    }

    public static @NotNull Engine start(@NotNull BaseObjectContainerRegistry objectContainerRegistry, @NotNull TaskScheduler scheduler) {
        EngineThread thread = new EngineThread(scheduler, objectContainerRegistry);
        Engine engine = new Engine(objectContainerRegistry, thread, scheduler);
        thread.start();
        return engine;
    }

    public void shutdown() {
        for (UUID key : objectContainerRegistry.getKeys())
            objectContainerRegistry.unregisterKey(key);

        if (thread instanceof EngineThread engineThread)
            engineThread.setRunning(false);
        else
            thread.interrupt();
    }
}
