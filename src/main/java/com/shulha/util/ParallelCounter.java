package com.shulha.util;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class ParallelCounter implements Runnable {
    private final AtomicLong sumOfNumbers;
    private final int[] randomNumberArray;

    public ParallelCounter(final int[] randomNumberArray, final AtomicLong sumOfNumbers) {
        this.randomNumberArray = randomNumberArray;
        this.sumOfNumbers = sumOfNumbers;
    }

    @Override
    public void run() {
        final int sum = Arrays.stream(randomNumberArray)
                .sum();
        System.out.println(Thread.currentThread().getName() + " gets sum: " + sum);
        sumOfNumbers.getAndAdd(sum);
    }
}
