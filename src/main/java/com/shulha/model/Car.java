package com.shulha.model;

import java.util.Random;

public class Car {
    private final static Random random = new Random();
    private final static int upperBound = 99_001;

    private String manufacturer;
    private String engine;
    private String color;
    private int count;
    private int price;

    public Car() {
        this("audi", "v12", "black");
    }

    public Car(final String manufacturer, final String engine, final String color) {
        this.manufacturer = manufacturer.toUpperCase().trim();
        this.engine = engine.toUpperCase().trim();
        this.color = color.toUpperCase().trim();
        count = 1;
        price = random.nextInt(upperBound + 1_000);
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(final String manufacturer) {
        if (manufacturer.isBlank()) {
            return;
        } else {
            this.manufacturer = manufacturer.toLowerCase().trim();
        }
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(final String engine) {
        if (engine.isBlank()) {
            return;
        } else {
            this.engine = engine.toLowerCase().trim();
        }
    }

    public String getColor() {
        return color;
    }

    public void setColor(final String color) {
        if (color.isBlank()) {
            return;
        } else {
            this.color = color.toLowerCase().trim();
        }
    }

    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        if (count == 0) {
            return;
        } else {
            this.count = count;
        }
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        if (price == 0) {
            return;
        } else {
            this.price = price;
        }
    }
}
