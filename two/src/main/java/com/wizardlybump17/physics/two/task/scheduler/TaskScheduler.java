package com.wizardlybump17.physics.two.task.scheduler;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.Timeable;
import com.wizardlybump17.physics.two.task.RegisteredTask;
import com.wizardlybump17.physics.two.task.factory.RegisteredTaskFactory;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
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
    private final @NotNull RegisteredTask headTask = new RegisteredTask();
    private final @NotNull AtomicReference<RegisteredTask> tailTask = new AtomicReference<>(headTask);
    private final @NotNull Map<Integer, RegisteredTask> runningTasks = new ConcurrentHashMap<>();

    public TaskScheduler(@NotNull RegisteredTaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    protected @NotNull RegisteredTask addTask(@NotNull RegisteredTask task) {
        RegisteredTask previous = tailTask.get();
        task.setPreviousTask(previous);
        setTailTask(task);
        return task;
    }

    protected void setTailTask(RegisteredTask task) {
        RegisteredTask tail = tailTask.get();
        while (!tailTask.compareAndSet(tail, task))
            tail = tailTask.get();
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

        for (task = tailTask.get(); task != null; task = task.getPreviousTask()) {
            if (task.getId() != id)
                continue;

            task.cancel();
            runningTasks.remove(id);
        }
    }

    public boolean isScheduled(int id) {
        if (runningTasks.containsKey(id))
            return true;
        for (RegisteredTask task = tailTask.get(); task != null; task = task.getPreviousTask())
            if (task.getId() == id)
                return !task.isCancelled();
        return false;
    }

    @Override
    public void tick() {
        start();

        long now = currentTick.get();
        RegisteredTask tail = tailTask.get();
        for (RegisteredTask task = tail; task != null; task = task.getPreviousTask()) {
            if (task.isInternal() || task.getNextRun() > now || task.isCancelled())
                continue;

            runningTasks.put(task.getId(), task);

            task.run();
            if (task.isRepeatable())
                task.setNextRun(now + task.getPeriod());
            else {
                runningTasks.remove(task.getId());
                task.cancel();
            }
        }

        currentTick.getAndIncrement();

        end();
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
