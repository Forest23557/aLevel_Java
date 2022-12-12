package com.shulha.action;

import com.shulha.model.CarTypes;
import com.shulha.util.UserInput;
import lombok.SneakyThrows;

import java.io.IOException;

public class CreateAction implements Action {
    private static final int DEFAULT_COUNT = 10;

    @SneakyThrows
    @Override
    public void execute() {
        String[] menu = {"Input your value", "Default value"};
        final int userChoice = UserInput.menu(menu);

        int count;
        if (userChoice == 0) {
            count = UserInput.count("Enter your count: ", "You wrote a wrong count!");
            CAR_SERVICE.createCar(count, CarTypes.CAR);
        } else {
            CAR_SERVICE.createCar(DEFAULT_COUNT, CarTypes.CAR);
            count = DEFAULT_COUNT;
        }

        System.out.printf("You have just created %d cars%n", count);
    }
}
