package com.wizardlybump17.physics.two;

import com.wizardlybump17.physics.two.task.scheduler.TaskScheduler;
import org.jetbrains.annotations.NotNull;

public final class Engine {

    private static TaskScheduler scheduler;

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
}
