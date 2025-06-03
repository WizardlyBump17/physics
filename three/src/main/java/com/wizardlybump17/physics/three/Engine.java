package com.wizardlybump17.physics.three;

import com.wizardlybump17.physics.task.scheduler.TaskScheduler;
import com.wizardlybump17.physics.three.registry.ShapesGroupsContainerRegistry;
import com.wizardlybump17.physics.three.thread.EngineThread;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class Engine {

    private final @NotNull ShapesGroupsContainerRegistry shapesGroupsContainerRegistry;
    private final @NotNull Thread thread;
    private final @NotNull TaskScheduler scheduler;

    public Engine(@NotNull ShapesGroupsContainerRegistry shapesGroupsContainerRegistry, @NotNull Thread thread, @NotNull TaskScheduler scheduler) {
        this.shapesGroupsContainerRegistry = shapesGroupsContainerRegistry;
        this.thread = thread;
        this.scheduler = scheduler;
    }

    public @NotNull ShapesGroupsContainerRegistry getShapesGroupsContainerRegistry() {
        return shapesGroupsContainerRegistry;
    }

    public @NotNull Thread getThread() {
        return thread;
    }

    public @NotNull TaskScheduler getScheduler() {
        return scheduler;
    }

    public static @NotNull Engine start(@NotNull ShapesGroupsContainerRegistry objectContainerRegistry, @NotNull TaskScheduler scheduler) {
        EngineThread thread = new EngineThread(scheduler, objectContainerRegistry);
        Engine engine = new Engine(objectContainerRegistry, thread, scheduler);
        thread.start();
        return engine;
    }

    public void shutdown() {
        for (UUID key : shapesGroupsContainerRegistry.getKeys())
            shapesGroupsContainerRegistry.unregisterKey(key);

        if (thread instanceof EngineThread engineThread)
            engineThread.setRunning(false);
        else
            thread.interrupt();
    }
}
