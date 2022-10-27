package com.shulha;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
//      1
        System.out.println("Task 1");

        Random rand = new Random();
        int upperBound = 100;

        int sideA = rand.nextInt(upperBound);
        int sideB = rand.nextInt(upperBound);
        int sideC = rand.nextInt(upperBound);

        if ((sideA < sideB + sideC) && (sideB < sideA + sideC) && (sideC < sideA + sideB)) {
            float p = (float) (sideA + sideB + sideC) / 2;
            int s = (int) Math.round(Math.sqrt(p * (p - sideA) * (p - sideB) * (p - sideC)));

            System.out.println("The area of the triangle with ");
            System.out.println("the side A: " + sideA + ", ");
            System.out.println("the side B: " + sideB);
            System.out.println("and the side C: " + sideC);
            System.out.println("equals - " + s);
            System.out.println();
        } else {
            System.out.println("We have wrong sides of the triangle!");
            System.out.println();
        }

//      2
        System.out.println("Task 2");

        int number1 = rand.nextInt(upperBound) - 50;
        int number2 = rand.nextInt(upperBound) - 50;
        int number3 = rand.nextInt(upperBound) - 50;

        System.out.println("Numbers 1: " + number1 + ", 2: " + number2 + ", 3: " + number3);

        int lowestNum = Math.abs(number1) < Math.abs(number2) ? number1 : number2;
        lowestNum = Math.abs(lowestNum) < Math.abs(number3) ? lowestNum : number3;

        System.out.println("The number module with the lowest value: " + Math.abs(lowestNum));
        System.out.println();

//      3
        System.out.println("Task 3");

        upperBound = 1000;
        int randomNumber = rand.nextInt(upperBound);

        if (randomNumber % 2 != 1 ) {
            System.out.println("The random number " + randomNumber + " is paired");
        } else {
            System.out.println("The random number " + randomNumber + " isn't paired");
        }

        System.out.println();

//      Additional task
        System.out.println("Additional task");

        upperBound = 255;
        double numberForChanging = rand.nextInt(upperBound);
        String binaryNumber = "";
        System.out.println("The decimal number: " + Math.round(numberForChanging));

        while (numberForChanging != 0) {
            if (numberForChanging % 2 == 1) {
                binaryNumber += 1;
            } else {
                binaryNumber += 0;
            }
            numberForChanging = Math.floor(numberForChanging / 2);
        }

        StringBuilder sb = new StringBuilder(binaryNumber);
        sb.reverse();
        binaryNumber = sb.toString();
        System.out.println("to the binary number equals: " + binaryNumber);
        System.out.println();
    }
}
