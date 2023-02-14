package com.shulha.builder;

import com.shulha.model.*;

public class TruckBuilder implements CarBuilder {
    private Truck truck;

    public TruckBuilder() {
        truck = new Truck();
    }

    public CarBuilder setLoadCapacity(final int loadCapacity) {
        truck.setLoadCapacity(loadCapacity);
        return this;
    }

    @Override
    public CarBuilder chooseManufacturer(final CarManufacturers carManufacturer) {
        truck.setManufacturer(carManufacturer);
        return this;
    }

    @Override
    public CarBuilder insertEngine(final int enginePower, final EngineTypes engineType) {
        truck.setEngine(new Engine(enginePower, engineType));
        return this;
    }

    @Override
    public CarBuilder paintCar(final CarColors color) {
        truck.setColor(color);
        return this;
    }

    @Override
    public CarBuilder setCount(final int count) {
        truck.setCount(count);
        checkCount();
        return this;
    }

    @Override
    public CarBuilder setPrice(final int price) {
        truck.setPrice(price);
        return this;
    }

    @Override
    public CarBuilder checkCount() {
        final int count = truck.getCount();

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
        final int price = truck.getPrice();

        if (price > MIN_PRICE) {
            System.out.println("We have correct price");
        } else {
            throw new IllegalArgumentException("Price should be more than " + MIN_PRICE + "!");
        }
        return this;
    }

    @Override
    public Car getCar() {
        return truck;
    }
}
