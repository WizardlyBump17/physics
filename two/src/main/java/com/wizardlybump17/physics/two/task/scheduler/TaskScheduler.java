package com.wizardlybump17.physics.two.task.scheduler;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.Timeable;
import com.wizardlybump17.physics.two.task.factory.RegisteredTaskFactory;
import com.wizardlybump17.physics.two.task.registered.RegisteredTask;
import com.wizardlybump17.physics.two.task.registered.RegisteredTaskImpl;
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
    private @NotNull RegisteredTaskImpl headTask = new RegisteredTaskImpl();
    private final @NotNull AtomicReference<RegisteredTaskImpl> tailTask = new AtomicReference<>(headTask);
    private final @NotNull Map<Integer, RegisteredTaskImpl> runningTasks = new ConcurrentHashMap<>();
    private final @NotNull Deque<RegisteredTaskImpl> pendingTasks = new ArrayDeque<>();
    private final @NotNull List<RegisteredTaskImpl> tempPendingTasks = new ArrayList<>();

    public TaskScheduler(@NotNull RegisteredTaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    protected @NotNull RegisteredTaskImpl addTask(@NotNull RegisteredTaskImpl task) {
        RegisteredTaskImpl previous = tailTask.get();
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
        RegisteredTaskImpl task = runningTasks.remove(id);
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
        for (RegisteredTaskImpl task = headTask; task != null; task = task.getNextTask())
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
            RegisteredTaskImpl task = pendingTasks.pollLast();
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
        RegisteredTaskImpl lastTask = null;
        for (RegisteredTaskImpl task = headTask.getNextTask(); task != null; task = (lastTask = task).getNextTask()) {
            pendingTasks.add(task);
            runningTasks.put(task.getId(), task);
        }

        for (RegisteredTaskImpl task = headTask; task != null; task = (lastTask = task).getNextTask())
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
