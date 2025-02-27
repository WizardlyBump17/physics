package com.wizardlybump17.physics.two.task.scheduler;

import com.wizardlybump17.physics.Tickable;
import com.wizardlybump17.physics.Timeable;
import com.wizardlybump17.physics.two.task.Task;
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
        AtomicUtil.set(tailTask, task).setNextTask(task);
        return task;
    }

    /**
     * <p>
     * Schedules the given task to be run on the NEXT tick.
     * </p>
     *
     * @param task the task to run
     * @return the {@link RegisteredTask} related to the task
     */
    public @NotNull RegisteredTask schedule(@NotNull Runnable task) {
        if (task instanceof Task)
            throw new UnsupportedOperationException("Use the Task#schedule(TaskScheduler) method instead");
        return addTask(taskFactory.create(nextTaskId(), task, currentTick.get() + 1));
    }

    /**
     * <p>
     * Schedules the given task to be run on the NEXT tick PLUS the given delay.
     * </p>
     *
     * @param task the task to run
     * @param delay the delay to wait to run the task
     * @return the {@link RegisteredTask} related to the task
     */
    public @NotNull RegisteredTask schedule(@NotNull Runnable task, long delay) {
        if (task instanceof Task)
            throw new UnsupportedOperationException("Use the Task#schedule(TaskScheduler, long) method instead");
        return addTask(taskFactory.create(nextTaskId(), task, delay, currentTick.get() + 1));
    }

    /**
     * <p>
     * Schedules the given task to be run on the NEXT tick PLUS the given delay.
     * The task will keep running with a period given by the {@code period} param.
     * </p>
     *
     * @param task the task to run
     * @param delay the delay to wait to run the task
     * @param period the number of ticks the task will keep running
     * @return the {@link RegisteredTask} related to the task
     */
    public @NotNull RegisteredTask schedule(@NotNull Runnable task, long delay, long period) {
        if (task instanceof Task)
            throw new UnsupportedOperationException("Use the Task#schedule(TaskScheduler, long, long) method instead");
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

            if (!task.isCancelled() && task.isRepeatable()) {
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
