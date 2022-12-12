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

        Optional<Car> optionalCar = Optional.ofNullable(car);
        if (optionalCar.isPresent()) {
            System.out.println("The car has been found: ");
            CAR_SERVICE.print(car);
        } else {
            System.out.println("A car with your ID is not found!");
        }
    }
}
