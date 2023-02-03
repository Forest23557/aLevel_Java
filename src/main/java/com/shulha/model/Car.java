package com.shulha.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "car")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Car implements CountRestore, Cloneable {
    private final static Random RANDOM = new Random();
    private final static int UPPER_BOUND = 99_001;
    @Id
//    @GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "UUID")
//    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "car_id")
    private String id;
    @Column(name = "car_type")
    @Setter(AccessLevel.PROTECTED)
    private CarTypes type;
    private CarManufacturers manufacturer;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "engine_id")
    private Engine engine;
    private CarColors color;
    @Setter(AccessLevel.NONE)
    private int count;
    @Setter(AccessLevel.NONE)
    private int price;

    public Car() {
        this(CarManufacturers.AUDI, new Engine(), CarColors.BLACK);
    }

    public Car(final CarManufacturers manufacturer, final Engine engine, final CarColors color) {
        count = 1;
        price = RANDOM.nextInt(UPPER_BOUND + 1_000);
        this.id = UUID.randomUUID().toString();

        if (manufacturer == null) {
            this.manufacturer = CarManufacturers.AUDI;
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return Objects.equals(id, car.id) && type == car.type && manufacturer == car.manufacturer;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, manufacturer);
    }

    @Override
    public String toString() {
        return String.format("ID: %s%nType of the car: %s%nManufacturer: %s%nEngine: %s%n" +
                "Color: %s%nPrice: %s%nCount: %s%n", id, type, manufacturer, engine.toString(), color, price, count);
    }

    @Override
    public Car clone() throws CloneNotSupportedException {
        return (Car) super.clone();
    }
}
