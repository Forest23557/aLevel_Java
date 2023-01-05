package com.shulha.action;

import com.shulha.model.Car;
import com.shulha.model.CarTypes;
import com.shulha.util.UserInput;
import lombok.SneakyThrows;

import java.util.Optional;
import java.util.Random;

public class FindAction implements Action {
    private static final Random RANDOM = new Random();

    @SneakyThrows
    @Override
    public void execute() {
        String[] menu = {"Enter car ID", "Random car"};
        final int userChoice = UserInput.menu(menu);

        Optional<Car> optionalCar;
        if (userChoice == 0) {
            final String id = UserInput.find("Write ID of your car: ", "You wrote an empty line!");
            optionalCar = CAR_SERVICE.find(id);
        } else {
            final Car[] cars = Optional.ofNullable(CAR_SERVICE.getAll())
                    .orElseThrow(
                            () -> new NullPointerException("You haven't created any cars! Our repository is empty!"));
            optionalCar = Optional.ofNullable(cars[RANDOM.nextInt(cars.length)]);
        }

        optionalCar.ifPresentOrElse(
                checkingCar -> {
                    System.out.println("The car has been found: ");
                    CAR_SERVICE.print(checkingCar);
                },
                () -> System.out.println("A car with your ID is not found!")
        );
    }
}
