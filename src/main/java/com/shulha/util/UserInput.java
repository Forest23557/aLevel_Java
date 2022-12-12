package com.shulha.util;

import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class UserInput {
    private static final BufferedReader READER =
            new BufferedReader(new InputStreamReader(System.in));

    @SneakyThrows
    public static int menu(final String[] names) throws IOException {
        int userChoice = -1;
        boolean condition = true;
        do {
            System.out.println("Choose your action: ");

            for (int i = 0; i < names.length; i++) {
                System.out.println(i + ". " + names[i]);
            }

            final String answer = READER.readLine();

            if (!StringUtils.isNumeric(answer)) {
                System.out.println("You wrote a wrong command! Enter an existing command from the display, please.");
                continue;
            }
            userChoice = Integer.parseInt(answer);

            condition = userChoice < 0 || userChoice >= names.length;
            if (condition) {
                System.out.println("You wrote a wrong command! Enter an existing command from the display, please.");
            }
        } while (condition);

        return userChoice;
    }

    @SneakyThrows
    public static int count(final String option, final String error) {
        int userCount = -1;
        boolean condition = true;
        do {
            System.out.println(option);

            final String answer = READER.readLine();

            if (!StringUtils.isNumeric(answer)) {
                System.out.println(error);
                continue;
            }
            userCount = Integer.parseInt(answer);

            condition = userCount < 1;
            if (condition) {
                System.out.println(error);
            }
        } while (condition);

        return userCount;
    }
}
