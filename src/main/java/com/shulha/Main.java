package com.shulha;

import com.shulha.action.Action;
import com.shulha.action.Actions;
import com.shulha.model.*;
import com.shulha.service.CarService;
import com.shulha.util.AlgorithmUtil;
import com.shulha.util.RandomGenerator;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class Main {
    private static BufferedReader reader =
            new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
//        final CarService carService = CarService.getInstance();
//
//        final Car car = carService.createCar(CarTypes.CAR);
//        final String id = car.getId();
//        carService.createCar(9, CarTypes.CAR);
//
//        carService.printAll();
//        final Car[] sortedCarArray = AlgorithmUtil.bubbleSort(carService.getAll());
//        System.out.println("~".repeat(20));
//        for (int i = 0; i < sortedCarArray.length; i++) {
//            carService.print(sortedCarArray[i]);
//        }
//
//        final Car car1 = AlgorithmUtil.binarySearch(sortedCarArray,
//                0, carService.getAll().length - 1, id);
//
//        System.out.println("~".repeat(20));
//        Optional.ofNullable(car1).ifPresent(car2 -> {
//            System.out.println(car2);
//        });

        final Actions[] values = Actions.values();

        int userChoice = -1;
        boolean condition = true;
        do {
            System.out.println("Choose your action: ");

            for (int i = 0; i < values.length; i++) {
                System.out.println(i + ". " + values[i].getName());
            }

            final String answer = reader.readLine();

            if (!StringUtils.isNumeric(answer)) {
                System.out.println("You wrote a wrong command! Enter an existing command from display, please.");
                continue;
            }
            userChoice = Integer.parseInt(answer);

            condition = userChoice < 0 || userChoice >= values.length;
            if (condition) {
                System.out.println("You wrote a wrong command! Enter an existing command from display, please.");
                continue;
            }
        } while (condition);

        values[userChoice].execute();
    }
}
