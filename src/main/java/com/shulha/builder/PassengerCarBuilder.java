package com.shulha.builder;

import com.shulha.model.*;

public class PassengerCarBuilder implements CarBuilder {
    private PassengerCar passengerCar;

    public PassengerCarBuilder() {
        passengerCar = new PassengerCar();
    }

    public CarBuilder setPassengerCount(final int passengerCount) {
        passengerCar.setPassengerCount(passengerCount);
        return this;
    }

    @Override
    public CarBuilder chooseManufacturer(final CarManufacturers carManufacturer) {
        passengerCar.setManufacturer(carManufacturer);
        return this;
    }

    @Override
    public CarBuilder insertEngine(final int enginePower, final EngineTypes engineType) {
        passengerCar.setEngine(new Engine(enginePower, engineType));
        return this;
    }

    @Override
    public CarBuilder paintCar(final CarColors color) {
        passengerCar.setColor(color);
        return this;
    }

    @Override
    public CarBuilder setCount(final int count) {
        passengerCar.setCount(count);
        checkCount();
        return this;
    }

    @Override
    public CarBuilder setPrice(final int price) {
        passengerCar.setPrice(price);
        return this;
    }

    @Override
    public CarBuilder checkCount() {
        final int count = passengerCar.getCount();

        if (count > 1) {
            System.out.printf("We have %s cars%n", count);
        } else if (count == 1) {
            System.out.printf("We have %s car%n", count);
        } else {
            throw new IllegalArgumentException("Car count must be more than 0!");
        }
        return this;
    }

    @Override
    public CarBuilder checkPrice() {
        final int price = passengerCar.getPrice();

        if (price > MIN_PRICE) {
            System.out.println("We have correct price");
        } else {
            throw new IllegalArgumentException("Price should be more than " + MIN_PRICE + "!");
        }
        return this;
    }

    @Override
    public Car getCar() {
        return passengerCar;
    }
}
