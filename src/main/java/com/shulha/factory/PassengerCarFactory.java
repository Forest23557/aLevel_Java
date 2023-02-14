package com.shulha.factory;

import com.shulha.builder.CarBuilder;
import com.shulha.builder.PassengerCarBuilder;
import com.shulha.model.Car;
import com.shulha.model.PassengerCar;

public class PassengerCarFactory extends CarFactory {
    @Override
    protected Car createCar() {
        return new PassengerCar();
    }

    @Override
    protected CarBuilder createCarBuilder() {
        return new PassengerCarBuilder();
    }
}
