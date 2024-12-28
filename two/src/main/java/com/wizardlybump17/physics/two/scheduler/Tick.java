package com.wizardlybump17.physics.two.scheduler;

import com.wizardlybump17.physics.two.scheduler.task.Task;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.Queue;

public final class Tick {

    private final long tick;
    private long start;
    private long end;
    private final @NotNull Queue<Task> tasks = new LinkedList<>();
    private final @NotNull Queue<Task> tasksToReschedule = new LinkedList<>();

    public Tick(long tick) {
        this.tick = tick;
    }

    public long getTick() {
        return tick;
    }

    public long getStart() {
        return start;
    }

    public long getEnd() {
        return end;
    }

    public @NotNull Queue<Task> getTasks() {
        return tasks;
    }

    public @NotNull Queue<Task> getTasksToReschedule() {
        return tasksToReschedule;
    }

    public void addTask(@NotNull Task task) {
        tasks.add(task);
    }

    public void addTasks(@NotNull Iterable<Task> tasks) {
        for (Task task : tasks)
            this.tasks.add(task);
    }

    void start() {
        start = System.currentTimeMillis();
    }

    void end() {
        end = System.currentTimeMillis();
    }

    public void tick() {
        Task task;
        while ((task = tasks.poll()) != null) {
            task.run();
            if (task.reschedule())
                tasksToReschedule.add(task);
        }
    }

    public long getElapsedTime() {
        return end - start;
    }
}
