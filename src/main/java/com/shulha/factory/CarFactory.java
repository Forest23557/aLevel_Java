package com.shulha.factory;

import com.shulha.builder.CarBuilder;
import com.shulha.model.Car;

abstract public class CarFactory {

    public Car getCar() {
        return createCar();
    }

    public CarBuilder getCarBuilder() {
        return createCarBuilder();
    }

    protected abstract Car createCar();

    protected abstract CarBuilder createCarBuilder();
}
