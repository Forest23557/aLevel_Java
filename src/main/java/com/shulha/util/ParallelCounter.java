package com.shulha.util;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class ParallelCounter implements Runnable {
    private final AtomicLong sumOfNumbers;
    private final int[] randomNumberArray;
    private final int numberOfThreads;
    private final int currentThread;
    private int upperBound;
    private int lowerBound;
    private int dividedArrayLength;

    public ParallelCounter(final int[] randomNumberArray, final AtomicLong sumOfNumbers,
                           final int numberOfThreads, final int currentThread) {
        this.randomNumberArray = randomNumberArray;
        this.sumOfNumbers = sumOfNumbers;
        this.numberOfThreads = numberOfThreads;
        this.currentThread = currentThread;
        setPartOfArrayLength();
        setUpperBound();
        setLowerBound();
    }

    @Override
    public void run() {
        int sum = 0;

        for (int i = lowerBound; i < upperBound; i++) {
            sum += randomNumberArray[i];
        }

//        final int sum = Arrays.stream(randomNumberArray)
//                .sum();
        System.out.println(Thread.currentThread().getName() + " gets sum: " + sum);
        sumOfNumbers.getAndAdd(sum);
    }

    private void setUpperBound() {
        upperBound = (currentThread + 1) * dividedArrayLength;
    }

    private void setLowerBound() {
        lowerBound = currentThread * dividedArrayLength;
    }

    private void setPartOfArrayLength() {
        dividedArrayLength = randomNumberArray.length / numberOfThreads;
    }
}
