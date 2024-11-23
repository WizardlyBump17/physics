package com.wizardlybump17.physics.two;

import com.wizardlybump17.physics.two.scheduler.Scheduler;
import org.jetbrains.annotations.NotNull;

public final class Engine {

    private static Scheduler scheduler;

    private Engine() {
    }

    public static Scheduler getScheduler() {
        return scheduler;
    }

    public static void setScheduler(@NotNull Scheduler scheduler) {
        if (Engine.scheduler != null)
            throw new IllegalStateException("The scheduler is already set");
        Engine.scheduler = scheduler;
    }
}
