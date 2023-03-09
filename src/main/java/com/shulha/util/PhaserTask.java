package com.shulha.util;

import lombok.SneakyThrows;

import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;

public class PhaserTask implements Runnable {
    private final Phaser phaser;

    public PhaserTask(final Phaser phaser) {
        this.phaser = phaser;
        phaser.register();
    }

    @SneakyThrows
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " is starting work...");

//      first phase
        System.out.println(Thread.currentThread().getName() + " is starting first phase");
        for (int i = 1; i <= 3; i++) {
            System.out.println(Thread.currentThread().getName() + " is executing first phase at " + i + " step");
        }
        System.out.println(Thread.currentThread().getName() + " has just finished first phase");
        phaser.arriveAndAwaitAdvance();
        TimeUnit.SECONDS.sleep(5);

//      second phase
        System.out.println(Thread.currentThread().getName() + " is starting second phase");
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + " is executing second phase at " + i + " step");
        }
        System.out.println(Thread.currentThread().getName() + " has just finished second phase");
        phaser.arriveAndAwaitAdvance();
        TimeUnit.SECONDS.sleep(5);

//      third phase
        System.out.println(Thread.currentThread().getName() + " is starting third phase");
        for (int i = 1; i <= 10; i++) {
            System.out.println(Thread.currentThread().getName() + " is executing third phase at " + i + " step");
        }
        System.out.println(Thread.currentThread().getName() + " has just finished third phase");

//      Deregister phaser
        phaser.arriveAndDeregister();
    }
}
