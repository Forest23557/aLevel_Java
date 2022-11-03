package com.shulha;

import java.util.Arrays;
import java.util.Random;

public class Main5 {
    public static void main(String[] args) {
        bubbleSort(10);
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
