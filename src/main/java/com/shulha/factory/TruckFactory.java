package com.shulha.factory;

import com.shulha.builder.CarBuilder;
import com.shulha.builder.TruckBuilder;
import com.shulha.model.Car;
import com.shulha.model.Truck;

public class TruckFactory extends CarFactory {
    @Override
    protected Car createCar() {
        return new Truck();
    }

    @Override
    protected CarBuilder createCarBuilder() {
        return new TruckBuilder();
    }
}
