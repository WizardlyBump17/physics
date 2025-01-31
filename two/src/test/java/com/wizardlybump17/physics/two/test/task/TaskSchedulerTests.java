package com.wizardlybump17.physics.two.test.task;

import com.wizardlybump17.physics.two.task.Task;
import com.wizardlybump17.physics.two.task.factory.RegisteredTaskFactory;
import com.wizardlybump17.physics.two.task.registered.RegisteredTask;
import com.wizardlybump17.physics.two.task.scheduler.TaskScheduler;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;

import java.util.concurrent.atomic.AtomicInteger;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TaskSchedulerTests {

    private static final @NotNull RegisteredTaskFactory REGISTERED_TASK_FACTORY = new RegisteredTaskFactory();
    private @NotNull TaskScheduler scheduler = new TaskScheduler(REGISTERED_TASK_FACTORY);

    @BeforeEach
    void resetScheduler() {
        scheduler = new TaskScheduler(REGISTERED_TASK_FACTORY);
    }

    @Test
    @Order(0)
    void testSchedule() {
        int tasks = 10;
        AtomicInteger executedTasks = new AtomicInteger();

        for (int i = 0; i < tasks; i++)
            scheduler.schedule(executedTasks::incrementAndGet);

        scheduler.tick();
        for (int i = 0; i < tasks; i++)
            scheduler.tick();

        Assertions.assertEquals(tasks, executedTasks.get());
    }

    @Test
    @Order(1)
    void testScheduleWithDelay() {
        int tasks = 10;
        long delay = 20;
        AtomicInteger executedTasks = new AtomicInteger();

        for (int i = 0; i < tasks; i++)
            scheduler.schedule(executedTasks::incrementAndGet, i * delay);

        scheduler.tick();
        for (int i = 0; i < tasks * delay; i++)
            scheduler.tick();

        Assertions.assertEquals(tasks, executedTasks.get());
    }

    @Test
    @Order(2)
    void testScheduleWithDelayAndPeriod() {
        int tasks = 10;
        long delay = 20;
        long period = 10;
        AtomicInteger executedTasks = new AtomicInteger();

        for (int i = 0; i < tasks; i++) {
            scheduler.schedule(
                    new Runnable() {
                        int count;

                        @Override
                        public void run() {
                            count++;
                            if (count >= tasks)
                                executedTasks.incrementAndGet();
                        }
                    },
                    delay,
                    period
            );
        }

        scheduler.tick();
        for (int i = 0; i < delay; i++)
            scheduler.tick();
        for (int i = 0; i < tasks * period; i++)
            scheduler.tick();

        Assertions.assertEquals(tasks, executedTasks.get());
    }

    @Test
    @Order(3)
    void testScheduleCancelAndIsScheduled() {
        int tasks = 10;
        AtomicInteger executedTasks = new AtomicInteger();

        for (int i = 0; i < tasks; i++) {
            RegisteredTask task = scheduler.schedule(executedTasks::incrementAndGet);
            Assertions.assertTrue(scheduler.isScheduled(task.getId()));
            task.cancel();
            Assertions.assertFalse(scheduler.isScheduled(task.getId()));
        }

        scheduler.tick();
        for (int i = 0; i < tasks; i++)
            scheduler.tick();

        Assertions.assertEquals(0, executedTasks.get());
    }

    @Test
    @Order(4)
    void testTaskSchedule() {
        int tasks = 10;
        AtomicInteger executedTasks = new AtomicInteger();

        for (int i = 0; i < tasks; i++) {
            new Task() {
                @Override
                public void run() {
                    executedTasks.incrementAndGet();
                }
            }.schedule(scheduler);
        }

        scheduler.tick();
        for (int i = 0; i < tasks; i++)
            scheduler.tick();

        Assertions.assertEquals(tasks, executedTasks.get());
    }

    @Test
    @Order(5)
    void testTaskScheduleWithDelay() {
        int tasks = 10;
        long delay = 20;
        AtomicInteger executedTasks = new AtomicInteger();

        for (int i = 0; i < tasks; i++) {
            new Task() {
                @Override
                public void run() {
                    executedTasks.incrementAndGet();
                }
            }.schedule(scheduler, delay);
        }

        scheduler.tick();
        for (int i = 0; i < tasks * delay; i++)
            scheduler.tick();

        Assertions.assertEquals(tasks, executedTasks.get());
    }

    @Test
    @Order(6)
    void testTaskScheduleWithDelayAndPeriod() {
        int tasks = 10;
        long delay = 20;
        long period = 10;
        AtomicInteger executedTasks = new AtomicInteger();

        for (int i = 0; i < tasks; i++) {
            new Task() {
                int count;

                @Override
                public void run() {
                    count++;
                    if (count >= tasks)
                        executedTasks.incrementAndGet();
                }
            }.schedule(scheduler, delay, period);
        }

        scheduler.tick();
        for (int i = 0; i < delay; i++)
            scheduler.tick();
        for (int i = 0; i < tasks * period; i++)
            scheduler.tick();

        Assertions.assertEquals(tasks, executedTasks.get());
    }

    @Test
    @Order(7)
    void testTaskScheduleCancelAndIsScheduled() {
        int tasks = 10;
        AtomicInteger executedTasks = new AtomicInteger();

        for (int i = 0; i < tasks; i++) {
            Task task = new Task() {
                @Override
                public void run() {
                    executedTasks.incrementAndGet();
                }
            };
            RegisteredTask registeredTask = task.schedule(scheduler);
            int id = registeredTask.getId();

            Assertions.assertTrue(scheduler.isScheduled(id));
            Assertions.assertTrue(task.isScheduled());
            registeredTask.cancel();
            Assertions.assertFalse(scheduler.isScheduled(id));
            Assertions.assertFalse(task.isScheduled());
        }

        Assertions.assertEquals(0, executedTasks.get());
    }
}
