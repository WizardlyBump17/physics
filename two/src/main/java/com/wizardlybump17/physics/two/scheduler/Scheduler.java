package com.wizardlybump17.physics.two.scheduler;

import com.wizardlybump17.physics.two.tick.Ticker;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;

public class Scheduler implements Ticker {

    private final @NotNull Queue<ScheduledTask> tasks = new LinkedList<>();

    public void schedule(@NotNull ScheduledTask task) {
        tasks.add(task);
    }

    //TODO: run as many tasks it can run
    @Override
    public void run() {
        ScheduledTask task;
        while ((task = tasks.poll()) != null) {
            task.run();
            if (task.reschedule())
                tasks.add(task);
        }
    }
}
