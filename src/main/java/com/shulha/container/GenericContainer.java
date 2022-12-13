package com.shulha.container;

import com.shulha.model.Car;
import com.shulha.model.PassengerCar;
import com.shulha.model.Truck;
import com.shulha.service.CarService;

import java.util.Optional;
import java.util.Random;

public class GenericContainer<T extends Car> {
    private static final Random RANDOM = new Random();
    private final T car;

    public GenericContainer(final T car) {
        this.car = car;
    }

    public void print() {
        System.out.println(car);
    }

    public void increaseCount() {
        car.setCount(RANDOM.nextInt(201) + 100);
    }

    public <N extends Number> void increaseCount(final N count) {
        if(Optional.ofNullable(count).isPresent()) {
            car.setCount(count.intValue());
        }
    }

    public static void main(String[] args) {
        GenericContainer<Car> passengerCarGen = new GenericContainer<>(new Truck());
        passengerCarGen.print();

        passengerCarGen.increaseCount();
        System.out.printf("~_~ ".repeat(15) + "%n%n");
        passengerCarGen.print();

        passengerCarGen.increaseCount(342.69967);
        System.out.printf("~_~ ".repeat(15) + "%n%n");
        passengerCarGen.print();
    }
}
