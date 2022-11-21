package com.shulha.model;

import java.util.Random;
import java.util.UUID;

public class Car {
    private final static Random RANDOM = new Random();
    private final static int UPPER_BOUND = 99_001;

    private final String id;
    private CarsManufacturers manufacturer;
    private Engine engine;
    private CarsColors color;
    private int count;
    private int price;

    public Car() {
        this(CarsManufacturers.AUDI, new Engine(), CarsColors.BLACK);
    }

    public Car(final CarsManufacturers manufacturer, final Engine engine, final CarsColors color) {
        this.manufacturer = manufacturer;
        this.engine = engine;
        this.color = color;
        count = 1;
        price = RANDOM.nextInt(UPPER_BOUND + 1_000);
        this.id = UUID.randomUUID().toString();
    }

    public String getId() {
        return id;
    }

    public CarsManufacturers getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(final CarsManufacturers manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Engine getEngine() {
        return engine;
    }

    public void setEngine(final Engine engine) {
        this.engine = engine;
    }

    public CarsColors getColor() {
        return color;
    }

    public void setColor(final CarsColors color) {
        this.color = color;
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        if (count <= 0) {
            return;
        } else {
            this.count = count;
        }
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        if (price <= 0) {
            return;
        } else {
            this.price = price;
        }
    }

    @Override
    public String toString() {
        return String.format("ID: %s%nManufacturer: %s%nEngine: %s%n" + "Color: %s%nPrice: %s%nCount: %s%n", id, manufacturer, engine.toString(), color, price, count);
    }
}
