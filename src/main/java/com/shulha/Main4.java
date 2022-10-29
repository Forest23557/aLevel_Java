package com.shulha;
import java.util.regex.*;

public class Main4 {
    public static void main(String[] args) {
//      1
        System.out.println("Task 1");
        String line = "Hello world!";
        System.out.println("Our string is: " + line);
        char first = line.charAt(line.indexOf("H"));
        int n = line.length();
        char last = line.charAt(n - 1);
        System.out.println("The first character is: " + first);
        System.out.println("The last character is: " + last);
        System.out.println();
    }
}
