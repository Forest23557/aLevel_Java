package com.shulha;

import com.shulha.action.Actions;
import com.shulha.builder.CarBuilder;
import com.shulha.builder.PassengerCarBuilder;
import com.shulha.builder.TruckBuilder;
import com.shulha.config.HibernateFactoryUtil;
import com.shulha.factory.CarFactory;
import com.shulha.factory.PassengerCarFactory;
import com.shulha.factory.TruckFactory;
import com.shulha.model.Car;
import com.shulha.model.CarColors;
import com.shulha.model.CarManufacturers;
import com.shulha.model.EngineTypes;
import com.shulha.util.AnnotationProcessor;
import com.shulha.util.UserInput;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    @SneakyThrows
    public static void main(String[] args) {
        LOGGER.info("The program has been started!");
        final AnnotationProcessor annotationProcessor = AnnotationProcessor.getInstance();

        final CarFactory carFactory = new PassengerCarFactory();
        final PassengerCarBuilder passengerCarBuilder = (PassengerCarBuilder) carFactory.getCarBuilder();
        final CarBuilder carBuilder = passengerCarBuilder.setPassengerCount(3);
        final Car car = carBuilder.chooseManufacturer(CarManufacturers.ACURA)
                .insertEngine(480, EngineTypes.CRDI)
                .paintCar(CarColors.AZURE)
                .setCount(1)
                .setPrice(1200)
                .checkPrice()
                .getCar();

        System.out.println(car);
        System.out.println("----".repeat(20));

        final CarFactory carFactory1 = new TruckFactory();
        final TruckBuilder truckBuilder = (TruckBuilder) carFactory1.getCarBuilder();
        final CarBuilder carBuilder1 = truckBuilder.setLoadCapacity(800);
        final Car car1 = carBuilder1.chooseManufacturer(CarManufacturers.ROVER)
                .insertEngine(800, EngineTypes.MPFI)
                .paintCar(CarColors.CHARCOAL)
                .setCount(2)
                .setPrice(3000)
                .checkPrice()
                .getCar();

        System.out.println(car1);

        final Actions[] values = Actions.values();
        String[] names = Arrays.stream(values)
                .map(value -> value.getName())
                .collect(Collectors.toCollection(LinkedList::new))
                .toArray(new String[0]);

        while (true) {
            final int userChoice = UserInput.menu(names);
            values[userChoice].execute();
        }
    }
}
