package com.shulha;

import java.util.Arrays;
import java.util.Random;

public class Main5 {
    public static void main(String[] args) {
//        task1(12);
        task2(8);
//        bubbleSort(10);
    }

//  Task 1
    private static void task1(int arrLength) {
        System.out.println("Task 1");

        int[] randomArray = new int[arrLength];
        final Random random = new Random();
        int index = 0;

        for (int i = 0; i < arrLength; i++) {
            randomArray[i] = random.nextInt(30) - 15;
        }

        System.out.println("Our array: " + Arrays.toString(randomArray));

        for (int i = 0; i < arrLength; i++) {
            if (randomArray[i] >= randomArray[index]) {
                index = i;
            }
        }

        System.out.printf("The index of the biggest number: %d%n", index);
        System.out.println();
    }

//  Task 2
    private static void task2(int arrLength) {
        System.out.println("Task 2");

        int[] randomArray = new int[arrLength];
        final Random random = new Random();

        for (int i = 0; i < arrLength; i++) {
            randomArray[i] = random.nextInt(10) + 1;
        }

        System.out.println("Our array: " + Arrays.toString(randomArray));

        for (int i = 1; i < arrLength; i += 2) {
            randomArray[i] = 0;
        }

        System.out.println("The array after changing: " + Arrays.toString(randomArray));
        System.out.println();
    }

//  Additional task
    private static void bubbleSort(int arrLength) {
        System.out.println("Additional task");

        int[] unsortedArray = new int[arrLength];
        final Random random = new Random();

        for (int i = 0; i < arrLength; i++) {
            unsortedArray[i] = random.nextInt(100);
        }

        System.out.println("Our unsorted array: " + Arrays.toString(unsortedArray));

        for (int i = 0; i < arrLength - 1; i++) {

            for (int j = 0; j < arrLength - i - 1; j++) {
                int firstNumber = unsortedArray[j];
                int secondNumber = unsortedArray[j + 1];

                if (firstNumber > secondNumber) {
                    int temp = firstNumber;
                    firstNumber = secondNumber;
                    secondNumber = temp;

                    unsortedArray[j] = firstNumber;
                    unsortedArray[j + 1] = secondNumber;
                }
            }
        }

        System.out.println("The array after sorting: " + Arrays.toString(unsortedArray));
        System.out.println();
    }
}
