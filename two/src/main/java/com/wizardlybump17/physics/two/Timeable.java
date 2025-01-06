package com.wizardlybump17.physics.two;

public interface Timeable {

    void start();

    void end();

    long getStartedAt();

    long getEndedAt();

    default long getElapsedTime() {
        return getEndedAt() - getStartedAt();
    }
}
