package com.wizardlybump17.physics.two.task.scheduler;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.Timeable;
import com.wizardlybump17.physics.two.task.RegisteredTask;
import com.wizardlybump17.physics.two.task.factory.RegisteredTaskFactory;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class TaskScheduler implements Tickable, Timeable {

    private long currentTick;
    private long start;
    private long end;
    private final @NotNull RegisteredTaskFactory taskFactory;
    private final @NotNull AtomicInteger taskIdCounter = new AtomicInteger();
    private RegisteredTask headTask = new RegisteredTask();
    private final @NotNull AtomicReference<RegisteredTask> tailTask = new AtomicReference<>(headTask);
    private final @NotNull PriorityQueue<RegisteredTask> pendingTasks = new PriorityQueue<>();
    private final @NotNull Map<Integer, RegisteredTask> runningTasks = new ConcurrentHashMap<>();
    private final @NotNull List<RegisteredTask> tempTasks = new ArrayList<>();

    public TaskScheduler(@NotNull RegisteredTaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    protected @NotNull RegisteredTask addTask(@NotNull RegisteredTask task) {
        RegisteredTask tail = tailTask.get();
        while (!tailTask.compareAndSet(tail, task)) //check if the variable references the same task as the actual tail task. If yes, set the tail task to be the new task
            tail = tailTask.get(); //if not, update the variable to reference the tail task
        tail.setNextTask(task);
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
        RegisteredTask task = runningTasks.get(id);
        if (task != null)
            task.setRunning(false);
    }

    protected void parsePending() {
        RegisteredTask head = headTask;
        RegisteredTask task = head.getNextTask();
        RegisteredTask lastTask = task;

        //loops through all tasks
        for (; task != null; task = (lastTask = task).getNextTask()) {
            if (!task.isRepeatable())
                continue;

            pendingTasks.add(task);
            runningTasks.put(task.getId(), task);
        }

        //loops through all tasks to clear their next task
        for (task = head; task != lastTask; task = head) {
            head = task.getNextTask();
            task.setNextTask(null);
        }

        headTask = lastTask == null ? tailTask.get() : lastTask;
    }

    @Override
    public void tick() {
        start();

        parsePending();
        while (canRun()) {
            RegisteredTask task = pendingTasks.remove();
            task.run();

            long period = task.getPeriod();
            if (period > 0) {
                task.setNextRun(currentTick + period);
                tempTasks.add(task);
            } else
                runningTasks.remove(task.getId());
        }

        pendingTasks.addAll(tempTasks);
        tempTasks.clear();

        currentTick++;

        end();
    }

    protected boolean canRun() {
        return !pendingTasks.isEmpty() && pendingTasks.peek().getNextRun() <= currentTick;
    }

    public long getCurrentTick() {
        return currentTick;
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
