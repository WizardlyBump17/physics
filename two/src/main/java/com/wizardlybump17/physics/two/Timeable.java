package com.wizardlybump17.physics.two;

import org.jetbrains.annotations.ApiStatus;

public interface Timeable {

    @ApiStatus.Internal
    void start();

    @ApiStatus.Internal
    void end();

    long getStartedAt();

    long getEndedAt();

    default long getElapsedTime() {
        return getEndedAt() - getStartedAt();
    }
}
