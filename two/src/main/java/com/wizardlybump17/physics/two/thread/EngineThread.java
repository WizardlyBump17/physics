package com.wizardlybump17.physics.two.thread;

import com.wizardlybump17.physics.two.Constants;
import com.wizardlybump17.physics.two.scheduler.Scheduler;
import org.jetbrains.annotations.NotNull;

public class EngineThread extends Thread {

    private final @NotNull Scheduler scheduler;
    private boolean running = true;

    public EngineThread(@NotNull Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public @NotNull Scheduler getScheduler() {
        return scheduler;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        long ticks = 0;
        while (running) {
            ticks++;
            tickScheduler();
            while (System.currentTimeMillis() < start + ticks * Constants.MILLIS_PER_TICK) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    protected void tickScheduler() {
        scheduler.run();
    }
}
