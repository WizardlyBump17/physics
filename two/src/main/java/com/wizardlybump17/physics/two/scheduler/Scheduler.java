package com.wizardlybump17.physics.two.scheduler;

import com.wizardlybump17.physics.two.scheduler.task.RunningTask;
import com.wizardlybump17.physics.two.scheduler.task.factory.RunningTaskFactory;
import com.wizardlybump17.physics.two.tick.Ticker;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Scheduler implements Ticker {

    private long currentTick;
    private long tickStart;
    private long tickEnd;
    private final @NotNull Map<Long, RunningTask> tasks = new HashMap<>();
    private final @NotNull RunningTaskFactory taskFactory;

    public Scheduler(@NotNull RunningTaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    protected @NotNull RunningTask addTask(@NotNull RunningTask task) {
        tasks.put(task.getId(), task);
        return task;
    }

    public @NotNull RunningTask schedule(@NotNull Runnable task) {
        return addTask(taskFactory.create(getTaskCount(), task, currentTick + 1));
    }

    public @NotNull RunningTask schedule(@NotNull Runnable task, long delay) {
        return addTask(taskFactory.create(getTaskCount(), task, delay, currentTick + 1));
    }

    public @NotNull RunningTask schedule(@NotNull Runnable task, long delay, long period) {
        return addTask(taskFactory.create(getTaskCount(), task, delay, period, currentTick + 1));
    }

    @Override
    public void run() {
        tickStart = System.currentTimeMillis();

        Iterator<RunningTask> taskIterator = tasks.values().iterator();
        while (taskIterator.hasNext()) {
            RunningTask task = taskIterator.next();
            if (!task.isRunning()) {
                taskIterator.remove();
                continue;
            }

            if (task.isTimeToRun(currentTick))
                task.run();

            if (!task.isRepeatable() || !task.isRunning())
                taskIterator.remove();
        }

        tickEnd = System.currentTimeMillis();

        currentTick++;
    }

    public long getCurrentTick() {
        return currentTick;
    }

    public @NotNull RunningTaskFactory getTaskFactory() {
        return taskFactory;
    }

    public long getTaskCount() {
        return tasks.size();
    }

    public long getTickStart() {
        return tickStart;
    }

    public long getTickEnd() {
        return tickEnd;
    }
}
