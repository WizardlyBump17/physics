package com.wizardlybump17.physics.two;

import com.wizardlybump17.physics.two.registry.BaseObjectContainerRegistry;
import com.wizardlybump17.physics.two.task.scheduler.TaskScheduler;
import org.jetbrains.annotations.NotNull;

public final class Engine {

    private static TaskScheduler scheduler;
    private static BaseObjectContainerRegistry objectContainerRegistry;

    private Engine() {
    }

    public static TaskScheduler getScheduler() {
        return scheduler;
    }

    public static void setScheduler(@NotNull TaskScheduler scheduler) {
        if (Engine.scheduler != null)
            throw new IllegalStateException("The scheduler is already set");
        Engine.scheduler = scheduler;
    }

    public static BaseObjectContainerRegistry getObjectContainerRegistry() {
        return objectContainerRegistry;
    }

    public static void setObjectContainerRegistry(@NotNull BaseObjectContainerRegistry objectContainerRegistry) {
        if (Engine.objectContainerRegistry != null)
            throw new IllegalStateException("The object container registry is already set.");
        Engine.objectContainerRegistry = objectContainerRegistry;
    }
}
