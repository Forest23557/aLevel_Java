package com.shulha.model;

import lombok.Getter;

import java.util.Random;

@Getter
public class Truck extends Car {
    private static final Random RANDOM = new Random();
    private int loadCapacity;

    public Truck() {
        this(CarManufacturers.ROVER, new Engine(), CarColors.WHITE, 500);
    }

    public Truck (final CarManufacturers manufacturer, final Engine engine, final CarColors color, final int loadCapacity) {
        super(manufacturer, engine, color);
        setType(CarTypes.TRUCK);

        if (loadCapacity < 100 || loadCapacity > 4000) {
            this.loadCapacity = RANDOM.nextInt(3901) + 100;
            return;
        }
        this.loadCapacity = loadCapacity;
    }

    public void setLoadCapacity(int loadCapacity) {
        if (loadCapacity < 100 || loadCapacity > 4000) {
            this.loadCapacity = RANDOM.nextInt(3901) + 100;
            return;
        }
        this.loadCapacity = loadCapacity;
    }

    @Override
    public void restore() {
        setCount(50);
        System.out.println("The count of trucks " + getManufacturer()
                + " with id " + getId() + " is " + getCount());
        System.out.println();
    }

    @Override
    public String toString() {
        return super.toString() + String.format("Load capacity: %s lbs%n", loadCapacity);
    }
}
