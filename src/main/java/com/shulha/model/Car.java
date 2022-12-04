package com.shulha.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;
import java.util.UUID;

@Setter
@Getter
public abstract class Car {
    private final static Random RANDOM = new Random();
    private final static int UPPER_BOUND = 99_001;

    private final String id;
    @Setter(AccessLevel.PROTECTED)
    private CarTypes type;
    private CarsManufacturers manufacturer;
    private Engine engine;
    private CarColors color;
    @Setter(AccessLevel.NONE)
    private int count;
    @Setter(AccessLevel.NONE)
    private int price;

    public Car() {
        this(CarsManufacturers.AUDI, new Engine(), CarColors.BLACK);
    }

    public Car(final CarsManufacturers manufacturer, final Engine engine, final CarColors color) {
        count = 1;
        price = RANDOM.nextInt(UPPER_BOUND + 1_000);
        this.id = UUID.randomUUID().toString();

        if (manufacturer == null) {
            this.manufacturer = CarsManufacturers.AUDI;
        } else {
            this.manufacturer = manufacturer;
        }

        if (engine == null) {
            this.engine = new Engine();
        } else {
            this.engine = engine;
        }

        if (color == null) {
            this.color = CarColors.BLACK;
        } else {
            this.color = color;
        }
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
        return String.format("ID: %s%nType of the car: %s%nManufacturer: %s%nEngine: %s%n" + "Color: %s%nPrice: %s%nCount: %s%n", id, type, manufacturer, engine.toString(), color, price, count);
    }
}
