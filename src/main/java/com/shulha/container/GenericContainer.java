package com.shulha.container;

import com.shulha.model.Car;
import com.shulha.model.PassengerCar;

import java.util.Optional;

public class GenericContainer<T extends Car> {
    private final T car;

    public GenericContainer(T car) {
        this.car = car;
    }


}
