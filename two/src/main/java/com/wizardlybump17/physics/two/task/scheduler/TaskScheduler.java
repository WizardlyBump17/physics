package com.wizardlybump17.physics.two.task.scheduler;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.Timeable;
import com.wizardlybump17.physics.two.task.RegisteredTask;
import com.wizardlybump17.physics.two.task.factory.RegisteredTaskFactory;
import com.wizardlybump17.physics.util.AtomicUtil;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class TaskScheduler implements Tickable, Timeable {

    private final @NotNull AtomicLong currentTick = new AtomicLong();
    private long start;
    private long end;
    private final @NotNull RegisteredTaskFactory taskFactory;
    private final @NotNull AtomicInteger taskIdCounter = new AtomicInteger();
    private @NotNull RegisteredTask headTask = new RegisteredTask();
    private final @NotNull AtomicReference<RegisteredTask> tailTask = new AtomicReference<>(headTask);
    private final @NotNull Map<Integer, RegisteredTask> runningTasks = new ConcurrentHashMap<>();
    private final @NotNull Deque<RegisteredTask> pendingTasks = new ArrayDeque<>();
    private final @NotNull List<RegisteredTask> tempPendingTasks = new ArrayList<>();

    public TaskScheduler(@NotNull RegisteredTaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    protected @NotNull RegisteredTask addTask(@NotNull RegisteredTask task) {
        RegisteredTask previous = tailTask.get();
        previous.setNextTask(task);
        AtomicUtil.set(tailTask, task);
        return task;
    }

    public @NotNull RegisteredTask schedule(@NotNull Runnable task) {
        return addTask(taskFactory.create(nextTaskId(), task, currentTick.get() + 1));
    }

    public @NotNull RegisteredTask schedule(@NotNull Runnable task, long delay) {
        return addTask(taskFactory.create(nextTaskId(), task, delay, currentTick.get() + 1));
    }

    public @NotNull RegisteredTask schedule(@NotNull Runnable task, long delay, long period) {
        return addTask(taskFactory.create(nextTaskId(), task, delay, period, currentTick.get() + 1));
    }

    public void cancelTask(int id) {
        RegisteredTask task = runningTasks.remove(id);
        if (task != null) {
            task.cancel();
            return;
        }

        for (task = headTask; task != null; task = task.getNextTask()) {
            if (task.getId() != id)
                continue;

            task.cancel();
            runningTasks.remove(id);
            return;
        }
    }

    public boolean isScheduled(int id) {
        if (runningTasks.containsKey(id))
            return true;
        for (RegisteredTask task = headTask; task != null; task = task.getNextTask())
            if (task.getId() == id)
                return !task.isCancelled();
        return false;
    }

    @Override
    public void tick() {
        start();

        long now = currentTick.get();

        parse();
        while (!pendingTasks.isEmpty()) {
            RegisteredTask task = pendingTasks.pollLast();
            if (task.isCancelled()) {
                parse();
                continue;
            }

            if (task.getNextRun() > now) {
                tempPendingTasks.add(task);
                parse();
                continue;
            }

            task.run();

            if (task.isRepeatable()) {
                task.setNextRun(now + task.getPeriod());
                tempPendingTasks.add(task);
            } else {
                task.cancel();
                runningTasks.remove(task.getId());
            }

            parse();
        }

        pendingTasks.addAll(tempPendingTasks);
        tempPendingTasks.clear();

        currentTick.getAndIncrement();

        end();
    }

    protected void parse() {
        RegisteredTask lastTask = null;
        for (RegisteredTask task = headTask.getNextTask(); task != null; task = (lastTask = task).getNextTask()) {
            pendingTasks.add(task);
            runningTasks.put(task.getId(), task);
        }

        for (RegisteredTask task = headTask; task != null; task = (lastTask = task).getNextTask())
            if (lastTask != null)
                lastTask.setNextTask(null);

        headTask = lastTask;
    }

    public long getCurrentTick() {
        return currentTick.get();
    }

    public @NotNull RegisteredTaskFactory getTaskFactory() {
        return taskFactory;
    }

    protected int nextTaskId() {
        return taskIdCounter.getAndIncrement();
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
