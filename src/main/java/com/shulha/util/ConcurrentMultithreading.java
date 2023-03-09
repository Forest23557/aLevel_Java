package com.shulha.util;

import lombok.SneakyThrows;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.*;

public class ConcurrentMultithreading {
    private static ExecutorService threadPool;

    private ConcurrentMultithreading() {
    }

    @SneakyThrows
    public static void executeTaskPhaser(final int threadNumber) {
        threadPool = Executors.newFixedThreadPool(threadNumber);
        final Phaser phaser = new Phaser(1);
        int currentPhase;

        for (int i = 0; i < threadNumber; i++) {
            final Runnable runnable = new PhaserTask(phaser);
            threadPool.execute(runnable);
        }

//      Waiting for first phase
        currentPhase = phaser.getPhase() + 1;
        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase " + currentPhase + " has just been finished");

//      Waiting for second phase
        currentPhase = phaser.getPhase() + 1;
        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase " + currentPhase + " has just been finished");

//      Waiting for third phase
        currentPhase = phaser.getPhase() + 1;
        phaser.arriveAndAwaitAdvance();
        System.out.println("Phase " + currentPhase + " has just been finished");

        phaser.arriveAndDeregister();

        if (phaser.isTerminated()) {
            System.out.println("Phaser has just finished its work");
        }
        threadPool.shutdown();
        System.out.println("The thread pool has been shut down");
    }

    @SneakyThrows
    public static void executeTaskCyclicBarrier(final int threadNumber) {
        threadPool = Executors.newFixedThreadPool(threadNumber);
        final Set<Future<Integer>> futureSet = new HashSet<>();
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(threadNumber);
        final CountDownLatch countDownLatch = new CountDownLatch(threadNumber * 3);

        for (int i = 0; i < threadNumber; i++) {
            final Callable<Integer> callable = new CyclicBarrierTask(cyclicBarrier, countDownLatch);
            final Future<Integer> integerFuture = threadPool.submit(callable);
            futureSet.add(integerFuture);
        }

        countDownLatch.await(5, TimeUnit.MINUTES);
        System.out.println("All phases have been finished");

        int sum = 0;

        for (Future<Integer> future : futureSet) {
            while (!future.isDone()) {
                System.out.println("Future is not ready yet");
                TimeUnit.MILLISECONDS.sleep(300);
            }
            final int integer = future.get();
            sum += integer;
        }

        System.out.println("All time that threads spent is " + sum + " seconds");
        threadPool.shutdown();
        System.out.println("The thread pool has been shut down");
    }

    public static void main(String[] args) {
        ConcurrentMultithreading.executeTaskPhaser(3);
        ConcurrentMultithreading.executeTaskCyclicBarrier(4);
    }
}
