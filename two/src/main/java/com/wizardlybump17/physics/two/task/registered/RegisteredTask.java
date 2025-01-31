package com.wizardlybump17.physics.two.task.registered;

import org.jetbrains.annotations.NotNull;

public interface RegisteredTask {

    int getId();

    @NotNull Runnable getRunnable();

    long getStartedAt();

    long getPeriod();

    long getDelay();

    void cancel();

    boolean isCancelled();
}
