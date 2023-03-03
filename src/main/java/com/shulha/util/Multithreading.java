package com.shulha.util;

import lombok.SneakyThrows;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Multithreading {
    private static final int[] RANDOM_NUMBERS = new int[100];
    private static final AtomicInteger INDEX = new AtomicInteger(99);
    private static final Set <Thread> THREAD_SET = ConcurrentHashMap.newKeySet();
    private static final int UPPER_BOUND = 99;
    private static final int LOWER_BOUND = 0;
    private static final int RANDOM_NUMBER_UPPER_BOUND = 1001;
    private static final AtomicLong SUM_OF_NUMBERS_FROM_ARRAY = new AtomicLong(0);



    private Multithreading() {
    }

    private static class GeneratingRandomNumberThread implements Runnable {
        private final ThreadLocalRandom random = ThreadLocalRandom.current();
        private int randomNumber;

        @Override
        public void run() {
            while (INDEX.get() >= LOWER_BOUND) {
                fillArray();
            }
        }

        private int getRandomNumber() {
            return random.nextInt(RANDOM_NUMBER_UPPER_BOUND);
        }

        private void updateRandomNumberIfItExists() {
            for (int i = UPPER_BOUND; i >= LOWER_BOUND; i--) {
                if (RANDOM_NUMBERS[i] == randomNumber) {
                    randomNumber = getRandomNumber();
                    i = UPPER_BOUND;
                }
            }
        }

        private void fillArray() {
            randomNumber = getRandomNumber();

            synchronized (GeneratingRandomNumberThread.class) {
                if (INDEX.get() >= LOWER_BOUND) {
                    updateRandomNumberIfItExists();

                    RANDOM_NUMBERS[INDEX.get()] = randomNumber;
                }

                System.out.println("Random number: " + randomNumber);
                System.out.println("Index: " + INDEX.get());
                System.out.println("The thread are working with array: " + Thread.currentThread().getName());
            }

            INDEX.getAndDecrement();
        }
    }

    public static void fillArrayWithRandomNumbers() {
        for (int i = 0; i < 10; i++) {
            final Runnable runnable = new Multithreading.GeneratingRandomNumberThread();

            final Thread thread = createAndStartThread(runnable);
            THREAD_SET.add(thread);

            System.out.println(thread.getName() + " has been added to THREAD_SET");
        }
    }

    @SneakyThrows
    public static void joinThreads() {
        for (Thread thread : THREAD_SET) {
            if (thread.isAlive()) {
                thread.join();
            }
        }
    }

    public static void countSumOfArrayNumbers(final int numberOfThreads) {
        checkNumberOfThreads(numberOfThreads);

        for (int currentThread = 0; currentThread < numberOfThreads; currentThread++) {
//            final int dividedArrayLength = RANDOM_NUMBERS.length / numberOfThreads;
//            final int upperBound = (currentThread + 1) * dividedArrayLength;
//            final int lowerBound = currentThread * dividedArrayLength;
//            final int[] partOfArray = Arrays.copyOfRange(RANDOM_NUMBERS, lowerBound, upperBound);
            final Runnable runnable = new ParallelCounter(RANDOM_NUMBERS, SUM_OF_NUMBERS_FROM_ARRAY,
                    numberOfThreads, currentThread);
            final Thread thread = createAndStartThread(runnable);

            THREAD_SET.add(thread);

            System.out.println(thread.getName() + " has been added to THREAD_SET");
        }
    }

    public static long getSumOfNumbersFromArray() {
        return SUM_OF_NUMBERS_FROM_ARRAY.longValue();
    }

    public static int[] getArray() {
        return RANDOM_NUMBERS.clone();
    }

    private static Thread createAndStartThread(final Runnable runnable) {
        final Thread thread = new Thread(runnable);
        thread.start();

        return thread;
    }

    private static void checkNumberOfThreads(final int numberOfThreads) {
        Optional.of(numberOfThreads)
                .filter(number -> RANDOM_NUMBERS.length % number == 0)
                .orElseThrow(
                        () -> new IllegalArgumentException("Number of threads must be a multiple of the array length")
                );
    }

    @SneakyThrows
    public static void main(String[] args) {
        Multithreading.fillArrayWithRandomNumbers();
        Multithreading.joinThreads();

        System.out.println("Our array: " + Arrays.toString(Multithreading.getArray()));

        final List<Integer> integers = new ArrayList<>();

        for (int number : Multithreading.getArray()) {
            integers.add(number);
        }

        final HashSet<Integer> integerHashSet = new HashSet<>(integers);
        System.out.println("Are numbers unique? - " + (integerHashSet.size() == 100));
        System.out.println("Size of set: " + integerHashSet.size());

        Multithreading.countSumOfArrayNumbers(4);
        Multithreading.joinThreads();

        final long sumOfNumbersFromArray = Multithreading.getSumOfNumbersFromArray();
        System.out.println("Entire sum of array: " + sumOfNumbersFromArray);

        long sum = 0;

        for (int number : RANDOM_NUMBERS) {
            sum += number;
        }

        System.out.println("Is the sum correct? - " + (sum == sumOfNumbersFromArray));
        System.out.println("Sum from loop: " + sum);
    }
}
