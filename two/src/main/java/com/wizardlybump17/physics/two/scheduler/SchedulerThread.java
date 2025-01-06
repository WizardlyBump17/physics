package com.wizardlybump17.physics.two.scheduler;

import com.wizardlybump17.physics.two.Constants;
import org.jetbrains.annotations.NotNull;

public class SchedulerThread extends Thread {

    private final @NotNull Scheduler scheduler;

    public SchedulerThread(@NotNull Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public @NotNull Scheduler getScheduler() {
        return scheduler;
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        long ticks = 1;
        while (true) {
            scheduler.run();
            while (System.currentTimeMillis() < (start + ticks * Constants.MILLIS_PER_TICK)) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            ticks++;
        }
    }
}
