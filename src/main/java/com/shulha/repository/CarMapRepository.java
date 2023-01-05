package com.shulha.repository;

import com.shulha.model.Car;
import com.shulha.model.CarTypes;
import com.shulha.service.CarService;

import java.util.*;
import java.util.function.BiPredicate;

public class CarMapRepository implements Repository<Car, String> {
    private static final Map<String, Car> CARS = new HashMap<>();
    private static final BiPredicate<String, String> CHECK_ID = (checkingId, id) -> checkingId.equals(id);
    private static CarMapRepository instance;

    private CarMapRepository() {
    }

    public static CarMapRepository getInstance() {
        if (instance == null) {
            instance = new CarMapRepository();
        }
        return instance;
    }

    @Override
    public void delete(final String id) {
        CARS.remove(id);
    }

    @Override
    public void save(final Car car) {
        Optional.ofNullable(car)
                .orElseThrow(NullPointerException::new);

        CARS.entrySet()
                .stream()
                .filter(entry -> CHECK_ID.test(entry.getKey(), car.getId()))
                .findAny()
                .ifPresentOrElse(
                        checkingEntry -> checkingEntry.getValue()
                                .setCount(checkingEntry.getValue().getCount() + car.getCount()),
                        () -> CARS.put(car.getId(), car)
                );
    }

    @Override
    public void removeAll() {
        CARS.clear();
    }

    @Override
    public Car[] getAll() {
        return CARS.values().toArray(new Car[0]);
    }

    @Override
    public Optional<Car> getById(final String id) {
        return CARS.entrySet()
                .stream()
                .filter(entry -> CHECK_ID.test(entry.getKey(), id))
                .map(Map.Entry::getValue)
                .findAny();
    }

//    public static void main(String[] args) {
//        final CarMapRepository carMapRepository = CarMapRepository.getInstance();
//        final CarService carService = CarService.getInstance();
//
//        carService.createCar(5, CarTypes.CAR);
//        carMapRepository.save(null);
//        carMapRepository.save(carService.getAll()[0]);
//        carMapRepository.save(carService.getAll()[1]);
//        carMapRepository.save(carService.getAll()[1]);
//        carMapRepository.save(carService.getAll()[2]);
//
//        carMapRepository.delete(carService.getAll()[1].getId());
//        carMapRepository.delete("gdf");
//        carMapRepository.delete("");
//        carMapRepository.delete(null);
//
//        System.out.println(carMapRepository.getById(carService.getAll()[1].getId()));
//        System.out.println(carMapRepository.getById("dfgsdfg"));
//        System.out.println(carMapRepository.getById(""));
//        System.out.println(carMapRepository.getById(null));
//
//        carMapRepository.removeAll();
//
//        System.out.println("~_~ ".repeat(20));
//        System.out.println(Arrays.toString(carMapRepository.getAll()));
//    }
}
