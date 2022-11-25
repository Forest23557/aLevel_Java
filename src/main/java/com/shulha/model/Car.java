package com.shulha.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.UUID;

@Setter
@Getter
public class Car {
    private final static Random RANDOM = new Random();
    private final static int UPPER_BOUND = 99_001;

    private final String id;
    private CarsManufacturers manufacturer;
    private Engine engine;
    private CarsColors color;
    @Setter(AccessLevel.NONE)
    private int count;
    @Setter(AccessLevel.NONE)
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

    public void setCount(final int count) {
        if (count <= 0) {
            return;
        }
        this.count = count;
    }

    public void setPrice(final int price) {
        if (price <= 0) {
            return;
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("ID: %s%nManufacturer: %s%nEngine: %s%n" + "Color: %s%nPrice: %s%nCount: %s%n", id, manufacturer, engine.toString(), color, price, count);
    }
}
