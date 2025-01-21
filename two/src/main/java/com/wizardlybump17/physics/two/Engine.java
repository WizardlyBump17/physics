package com.wizardlybump17.physics.two;

import com.wizardlybump17.physics.two.registry.BaseObjectContainerRegistry;
import com.wizardlybump17.physics.two.task.scheduler.TaskScheduler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class Engine {

    private static TaskScheduler scheduler;
    private static BaseObjectContainerRegistry objectContainerRegistry;
    private static Thread thread;

    private Engine() {
    }

    public static TaskScheduler getScheduler() {
        return scheduler;
    }

    public static void setScheduler(@NotNull TaskScheduler scheduler) {
        assertNotSet(Engine.scheduler, "scheduler");
        Engine.scheduler = scheduler;
    }

    public static BaseObjectContainerRegistry getObjectContainerRegistry() {
        return objectContainerRegistry;
    }

    public static void setObjectContainerRegistry(@NotNull BaseObjectContainerRegistry objectContainerRegistry) {
        assertNotSet(Engine.objectContainerRegistry, "object container registry");
        Engine.objectContainerRegistry = objectContainerRegistry;
    }

    private static void assertNotSet(@Nullable Object object, @NotNull String what) {
        if (object != null)
            throw new IllegalStateException("The " + what + " is already set.");
    }

    public static void setThread(@NotNull Thread thread) {
        assertNotSet(Engine.thread, "thread");
        Engine.thread = thread;
    }

    public static Thread getThread() {
        return thread;
    }
}
