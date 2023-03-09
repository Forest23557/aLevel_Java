package com.shulha.util;

import lombok.SneakyThrows;

import java.util.Optional;
import java.util.concurrent.*;

public class CyclicBarrierTask implements Callable<Integer> {
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private CyclicBarrier cyclicBarrier;
    private CountDownLatch countDownLatch;

    public CyclicBarrierTask(final CyclicBarrier cyclicBarrier, final CountDownLatch countDownLatch) {
        this.cyclicBarrier = cyclicBarrier;
        this.countDownLatch = countDownLatch;
    }

    @SneakyThrows
    @Override
    public Integer call() {
        int timeCounter = 0;
        int sleepingTime;
        System.out.println(Thread.currentThread().getName() + " is starting its work...");

//      first phase
        System.out.println(Thread.currentThread().getName() + " is starting first phase...");

        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " is executing first phase at " + i + " step");
        }

        sleepingTime = random.nextInt(11);
        timeCounter += sleepingTime;
        TimeUnit.SECONDS.sleep(sleepingTime);

        System.out.println(Thread.currentThread().getName() + " has finished first phase for " +
                sleepingTime + "seconds");
        cyclicBarrier.await(2, TimeUnit.MINUTES);
        countDownLatch.countDown();

//      second phase
        System.out.println(Thread.currentThread().getName() + " is starting second phase...");

        for (int i = 0; i < 7; i++) {
            System.out.println(Thread.currentThread().getName() + " is executing second phase at " + i + " step");
        }

        sleepingTime = random.nextInt(11);
        timeCounter += sleepingTime;
        TimeUnit.SECONDS.sleep(sleepingTime);

        System.out.println(Thread.currentThread().getName() + " has finished second phase for " +
                sleepingTime + "seconds");
        cyclicBarrier.await(2, TimeUnit.MINUTES);
        countDownLatch.countDown();

//      third phase
        System.out.println(Thread.currentThread().getName() + " is starting third phase...");

        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " is executing third phase at " + i + " step");
        }

        sleepingTime = random.nextInt(11);
        timeCounter += sleepingTime;
        TimeUnit.SECONDS.sleep(sleepingTime);

        System.out.println(Thread.currentThread().getName() + " has finished third phase for " +
                sleepingTime + "seconds");
        cyclicBarrier.await(2, TimeUnit.MINUTES);
        countDownLatch.countDown();

        return timeCounter;
    }
}
