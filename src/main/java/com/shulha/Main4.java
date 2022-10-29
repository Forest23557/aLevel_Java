package com.shulha;
import java.util.Scanner;
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

//      2.1
        System.out.println("Task 2");

        String substring = "se";

        String string = "Java Exercises";
        System.out.println("Does the string \"" + string + "\" ends with \"" + substring + "\"?");
        System.out.println(string.endsWith(substring));

        String string2 = "Java Exercise";
        System.out.println("Does the string \"" + string2 + "\" ends with \"" + substring + "\"?");
        System.out.println(string2.endsWith(substring));
        System.out.println();
//      2.2
        String myString = "Stephen Edwin King";
        Scanner scanner = new Scanner(System.in);

        System.out.println("Write your string: ");
        String inputString = scanner.next();
        System.out.println("Does the string \"" + myString + "\" has the substring \"" + inputString + "\"?");
        System.out.println(myString.indexOf(inputString) >= 0 ? true : false);
    }
}
