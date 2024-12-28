package com.wizardlybump17.physics.two.scheduler;

import com.wizardlybump17.physics.two.scheduler.task.Task;
import com.wizardlybump17.physics.two.tick.Ticker;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;

public class Scheduler implements Ticker {

    private final @NotNull Queue<Task> tasks = new LinkedList<>();

    public void schedule(@NotNull Task task) {
        tasks.add(task);
    }

    //TODO: run as many tasks it can run
    @Override
    public void run() {
        Task task;
        while ((task = tasks.poll()) != null) {
            task.run();
            if (task.reschedule())
                tasks.add(task);
        }
    }
}
