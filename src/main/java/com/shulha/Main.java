package com.shulha;

public class Main {
    public static void main(String[] args) {
//      1
        String name = "Peter";
        String surname = "Shulha";
        System.out.println(name + " " + surname);

//      2
        int y = 5;
        for (int i = 0; i <= 10; i++) {
            System.out.println("The step " + i + ", the value " + y);
            y += 2;
        }

//      3
        for (int i = 0; i <= 10; i++) {
            if (i == 3) {
                continue;
            } else if (i == 6) {
                break;
            }
            System.out.println("The step " + i);
        }
    }
}
