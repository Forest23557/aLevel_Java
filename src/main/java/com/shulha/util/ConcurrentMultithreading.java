package com.shulha.util;

import lombok.SneakyThrows;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class ConcurrentMultithreading {
    private static ExecutorService threadPool;

    private ConcurrentMultithreading() {
    }

    @SneakyThrows
    public static void executeTask(final int threadNumber) {
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

    public static void main(String[] args) {
        ConcurrentMultithreading.executeTask(3);
    }
}
