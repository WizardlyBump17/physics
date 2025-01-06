package com.wizardlybump17.physics.two.task.scheduler;

import com.wizardlybump17.physics.two.task.RegisteredTask;
import com.wizardlybump17.physics.two.task.factory.RegisteredTaskFactory;
import com.wizardlybump17.physics.two.tick.Ticker;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TaskScheduler implements Ticker {

    private long currentTick;
    private long tickStart;
    private long tickEnd;
    private final @NotNull Map<Long, RegisteredTask> tasks = new HashMap<>();
    private final @NotNull Map<Long, RegisteredTask> pendingTasks = new HashMap<>();
    private final @NotNull RegisteredTaskFactory taskFactory;
    private long taskCounter;

    public TaskScheduler(@NotNull RegisteredTaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    protected @NotNull RegisteredTask addTask(@NotNull RegisteredTask task) {
        pendingTasks.put(task.getId(), task);
        return task;
    }

    public @NotNull RegisteredTask schedule(@NotNull Runnable task) {
        return addTask(taskFactory.create(nextTaskId(), task, currentTick + 1));
    }

    public @NotNull RegisteredTask schedule(@NotNull Runnable task, long delay) {
        return addTask(taskFactory.create(nextTaskId(), task, delay, currentTick + 1));
    }

    public @NotNull RegisteredTask schedule(@NotNull Runnable task, long delay, long period) {
        return addTask(taskFactory.create(nextTaskId(), task, delay, period, currentTick + 1));
    }

    public void cancelTask(long id) {
        RegisteredTask task = tasks.get(id);
        task.setRunning(false);
    }

    @Override
    public void run() {
        tickStart = System.currentTimeMillis();

        tasks.putAll(pendingTasks);
        pendingTasks.clear();
        Iterator<RegisteredTask> taskIterator = tasks.values().iterator();
        while (taskIterator.hasNext()) {
            RegisteredTask task = taskIterator.next();
            if (!task.isRunning()) {
                taskIterator.remove();
                continue;
            }

            if (task.getStartedAt() > currentTick)
                continue;

            if (task.isTimeToRun(currentTick)) {
                task.run();
                if (!task.isRepeatable())
                    task.setRunning(false);
            }

            if (!task.isRunning())
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

    public @NotNull RegisteredTaskFactory getTaskFactory() {
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
