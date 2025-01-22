package com.wizardlybump17.physics.two.task.scheduler;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.Timeable;
import com.wizardlybump17.physics.two.task.RegisteredTask;
import com.wizardlybump17.physics.two.task.factory.RegisteredTaskFactory;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class TaskScheduler implements Tickable, Timeable {

    private long currentTick;
    private long start;
    private long end;
    private final @NotNull Map<Integer, RegisteredTask> tasks = new HashMap<>();
    private final @NotNull Map<Integer, RegisteredTask> pendingTasks = new HashMap<>();
    private final @NotNull RegisteredTaskFactory taskFactory;
    private final @NotNull AtomicInteger taskCounter = new AtomicInteger();

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

    public void cancelTask(int id) {
        RegisteredTask task = tasks.get(id);
        task.setRunning(false);
    }

    @Override
    public void tick() {
        start();

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

        currentTick++;

        end();
    }

    public long getCurrentTick() {
        return currentTick;
    }

    public @NotNull RegisteredTaskFactory getTaskFactory() {
        return taskFactory;
    }

    protected int nextTaskId() {
        return taskCounter.getAndIncrement();
    }

    @Override
    public void start() {
        start = System.currentTimeMillis();
    }

    @Override
    public void end() {
        end = System.currentTimeMillis();
    }

    @Override
    public long getStartedAt() {
        return start;
    }

    @Override
    public long getEndedAt() {
        return end;
    }
}
