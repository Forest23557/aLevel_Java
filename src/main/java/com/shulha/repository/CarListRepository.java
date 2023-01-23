package com.shulha.repository;

import com.shulha.annotation.Singleton;
import com.shulha.model.Car;
import com.shulha.model.CarColors;
import com.shulha.model.CarTypes;
import com.shulha.model.PassengerCar;
import com.shulha.service.CarService;

import java.util.*;
import java.util.function.BiPredicate;

@Singleton
public class CarListRepository implements Repository<Car, String> {
    private static final List<Car> CARS = new LinkedList<>();
    private static final BiPredicate<Car, String> CHECK_ID = (car, id) -> car.getId().equals(id);
    private static CarListRepository instance;

    private CarListRepository() {
    }

    public static CarListRepository getInstance() {
        if (instance == null) {
            instance = new CarListRepository();
        }
        return instance;
    }

    @Override
    public void delete(final String id) {
        CARS.stream()
                .dropWhile(Objects::isNull)
                .filter(checkingCar -> CHECK_ID.test(checkingCar, id))
                .findAny()
                .ifPresent(CARS::remove);
    }

    @Override
    public void save(final Car car) {
        CARS.stream()
                .dropWhile(Objects::isNull)
                .filter(checkingCar -> CHECK_ID.test(checkingCar, car.getId()))
                .findAny()
                .ifPresentOrElse(
                        checkedCar -> checkedCar.setCount(car.getCount() + checkedCar.getCount()),
                        () -> CARS.add(car)
                );
    }

    @Override
    public void removeAll() {
        CARS.removeAll(CARS);
    }

    @Override
    public Car[] getAll() {
        return CARS.toArray(new Car[0]);
    }

    @Override
    public Optional<Car> getById(final String id) {
        return CARS.stream()
                .dropWhile(Objects::isNull)
                .filter(checkingCar -> CHECK_ID.test(checkingCar, id))
                .findAny();
    }

//    public static void main(String[] args) {
//        final CarListRepository carListRepository = CarListRepository.getInstance();
//        final CarService carService = CarService.getInstance();
//        carService.createCar(3, CarTypes.CAR);
//        carListRepository.save(null);
//        carListRepository.save(null);
//        carListRepository.save(carService.getAll()[0]);
//        carListRepository.save(carService.getAll()[1]);
//        carListRepository.save(carService.getAll()[1]);
//        System.out.println(carListRepository.getById(carService.getAll()[1].getId()));
//        System.out.println(carListRepository.getById("sdfgw"));
//        System.out.println(carListRepository.getById(""));
//        System.out.println(carListRepository.getById(null));
//        System.out.println("~_~ ".repeat(20));
//        carListRepository.delete(carService.getAll()[1].getId());
//        carListRepository.delete(carService.getAll()[0].getId());
//        carListRepository.delete("dfsag");
//        carListRepository.delete(" ");
//        carListRepository.delete(null);
//        carListRepository.removeAll();
//        System.out.println(Arrays.toString(carListRepository.getAll()));
//    }
}
