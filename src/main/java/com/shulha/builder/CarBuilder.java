package com.shulha.builder;

import com.shulha.model.*;
public abstract class CarBuilder<T extends CarBuilder> implements Builder {
    protected Car car;

    protected CarBuilder(final Car car) {
        this.car = car;
    }

    @Override
    public Builder chooseManufacturer(final CarManufacturers carManufacturer) {
        car.setManufacturer(carManufacturer);
        return this;
    }

    @Override
    public Builder insertEngine(final int enginePower, final EngineTypes engineType) {
        car.setEngine(new Engine(enginePower, engineType));
        return this;
    }

    @Override
    public Builder paintCar(final CarColors color) {
        car.setColor(color);
        return this;
    }

    @Override
    public Builder setCount(final int count) {
        checkCount(count);
        car.setCount(count);
        return this;
    }

    @Override
    public Builder setPrice(final int price) {
        checkPrice(price);
        car.setPrice(price);
        return this;
    }

    public abstract T getBuilder();

    private void checkCount(final int count) {
        if (count > 1) {
            System.out.printf("We have %s cars%n", count);
        } else if (count == 1) {
            System.out.printf("We have %s car%n", count);
        } else {
            throw new IllegalArgumentException("Car count must be more than 0!");
        }
    }

    private void checkPrice(final int price) {
        if (price > MIN_PRICE) {
            System.out.println("We have correct price");
        } else {
            throw new IllegalArgumentException("Price should be more than " + MIN_PRICE + "!");
        }
    }

    @Override
    public Car getCar() {
        return car;
    }
}
