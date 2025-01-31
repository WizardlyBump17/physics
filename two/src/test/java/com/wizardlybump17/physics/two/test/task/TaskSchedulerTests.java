package com.wizardlybump17.physics.two.test.task;

import com.wizardlybump17.physics.two.task.factory.RegisteredTaskFactory;
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
                        int count = 0;

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
}
