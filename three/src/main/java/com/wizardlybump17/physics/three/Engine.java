package com.wizardlybump17.physics.three;

import com.wizardlybump17.physics.task.scheduler.TaskScheduler;
import com.wizardlybump17.physics.three.registry.BaseObjectContainerRegistry;
import com.wizardlybump17.physics.three.thread.EngineThread;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public final class Engine {

    private static BaseObjectContainerRegistry objectContainerRegistry;
    private static Thread thread;
    private static TaskScheduler scheduler;

    private Engine() {
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

    private static void assertSet(@Nullable Object object, @NotNull String what) {
        if (object == null)
            throw new IllegalStateException("The " + what + " is not set.");
    }

    public static Thread getThread() {
        return thread;
    }

    public static void setThread(Thread thread) {
        assertNotSet(Engine.thread, "thread");
        Engine.thread = thread;
    }

    public static TaskScheduler getScheduler() {
        return scheduler;
    }

    public static void setScheduler(TaskScheduler scheduler) {
        assertNotSet(Engine.scheduler, "scheduler");
        Engine.scheduler = scheduler;
    }

    public static void start(@NotNull BaseObjectContainerRegistry objectContainerRegistry, @NotNull Thread thread, @NotNull TaskScheduler scheduler) {
        setObjectContainerRegistry(objectContainerRegistry);
        setThread(thread);
        setScheduler(scheduler);
    }

    public static void shutdown() {
        assertSet(objectContainerRegistry, "object container registry");
        assertSet(thread, "thread");
        assertSet(scheduler, "scheduler");

        for (UUID key : objectContainerRegistry.getKeys())
            objectContainerRegistry.unregisterKey(key);
        objectContainerRegistry = null;

        thread.interrupt();
        if (thread instanceof EngineThread engineThread)
            engineThread.setRunning(false);
    }
}
