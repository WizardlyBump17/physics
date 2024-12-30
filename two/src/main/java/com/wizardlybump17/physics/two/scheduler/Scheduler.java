package com.wizardlybump17.physics.two.scheduler;

import com.wizardlybump17.physics.two.scheduler.task.AbstractTask;
import com.wizardlybump17.physics.two.scheduler.task.Task;
import com.wizardlybump17.physics.two.tick.Ticker;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.TreeMap;

public class Scheduler implements Ticker {

    private long tickCounter;
    private long taskCounter;
    private @NotNull Tick currentTick = new Tick(tickCounter++);
    private @NotNull Tick nextTick = new Tick(tickCounter++);
    private final @NotNull Map<Long, Tick> scheduledTicks = new TreeMap<>();
    private final @NotNull Map<Long, AbstractTask> tasks = new TreeMap<>();

    public Scheduler() {
        scheduledTicks.put(currentTick.getTick(), currentTick);
        scheduledTicks.put(nextTick.getTick(), nextTick);
    }

    public long schedule(@NotNull AbstractTask task) {
        tasks.put(taskCounter++, task);
        return taskCounter - 1;
    }

    public long schedule(@NotNull AbstractTask task, long delay) {
        long id = schedule(task);
        schedule(task::run, delay);
        return id;
    }

    public long schedule(@NotNull Task task) {
        nextTick.addTask(task);
        return taskCounter++;
    }

    public long schedule(@NotNull Task task, long delay) {
        if (delay < 2)
            nextTick.addTask(task);
        else
            scheduledTicks.computeIfAbsent(tickCounter + delay, $ -> new Tick(tickCounter + delay)).addTask(task);
        return taskCounter++;
    }

    public long schedule(@NotNull Runnable task, long delay, long period) {
        if (period < 1)
            return schedule(task::run, delay);

        long targetTick = tickCounter + delay;
        schedule(new Task() {
            @Override
            public void run() {
                task.run();
            }

            @Override
            public boolean canRun() {
                return (tickCounter - targetTick) % period == 0;
            }

            @Override
            public boolean isPersistent() {
                return true;
            }
        }, delay);
        return taskCounter++;
    }

    @Override
    public void run() {
        Tick currentTick = this.currentTick;
        currentTick.start();

        currentTick.tick();

        scheduledTicks.remove(currentTick.getTick());

        this.currentTick = this.nextTick;
        this.currentTick.addTasks(currentTick.getTasksToReschedule());

        this.nextTick = scheduledTicks.computeIfAbsent(++tickCounter, $ -> new Tick(tickCounter));

        currentTick.end();
//
//        long elapsedTime = currentTick.getElapsedTime();
//        if (elapsedTime > Constants.MILLIS_PER_TICK)
//            System.out.println(Constants.MILLIS_PER_TICK / elapsedTime * Constants.TICKS_PER_SECOND);
//        else
//            System.out.println(Constants.TICKS_PER_SECOND);
    }

    public long getCurrentTick() {
        return currentTick.getTick();
    }
}
