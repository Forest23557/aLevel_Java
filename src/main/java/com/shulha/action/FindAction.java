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

        Car car = null;
        if (userChoice == 0) {
            final String id = UserInput.find("Write ID of your car: ", "You wrote an empty line!");
            car = CAR_SERVICE.find(id);
        } else {
            Optional<Car[]> optionalCars = Optional.ofNullable(CAR_SERVICE.getAll());
            if (optionalCars.isPresent()) {
                final Car[] cars = optionalCars.get();
                car = cars[RANDOM.nextInt(cars.length)];
            } else {
                System.out.println("You haven't created any cars! Our repository is empty!");
            }
        }

        final Car finalCar = car;
        Optional.ofNullable(car)
                .ifPresent(car1 -> {
                    System.out.println("The car has been found: ");
                    CAR_SERVICE.print(finalCar);
                });
    }
}
