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
    private final @NotNull Map<Long, RunningTask> pendingTasks = new HashMap<>();
    private final @NotNull RunningTaskFactory taskFactory;
    private long taskCounter;

    public Scheduler(@NotNull RunningTaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    protected @NotNull RunningTask addTask(@NotNull RunningTask task) {
        pendingTasks.put(task.getId(), task);
        return task;
    }

    public @NotNull RunningTask schedule(@NotNull Runnable task) {
        return addTask(taskFactory.create(nextTaskId(), task, currentTick + 1));
    }

    public @NotNull RunningTask schedule(@NotNull Runnable task, long delay) {
        return addTask(taskFactory.create(nextTaskId(), task, delay, currentTick + 1));
    }

    public @NotNull RunningTask schedule(@NotNull Runnable task, long delay, long period) {
        return addTask(taskFactory.create(nextTaskId(), task, delay, period, currentTick + 1));
    }

    public void cancelTask(long id) {
        RunningTask task = tasks.get(id);
        task.setRunning(false);
    }

    @Override
    public void run() {
        tickStart = System.currentTimeMillis();

        tasks.putAll(pendingTasks);
        pendingTasks.clear();
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
        pendingTasks.putAll(tasks);
        tasks.clear();

        tickEnd = System.currentTimeMillis();

        currentTick++;
    }

    public long getCurrentTick() {
        return currentTick;
    }

    public @NotNull RunningTaskFactory getTaskFactory() {
        return taskFactory;
    }

    protected long nextTaskId() {
        return taskCounter++;
    }

    public long getTickStart() {
        return tickStart;
    }

    public long getTickEnd() {
        return tickEnd;
    }
}
