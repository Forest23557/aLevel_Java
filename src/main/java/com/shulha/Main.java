package com.shulha;

import com.shulha.model.Car;
import com.shulha.model.CarTypes;
import com.shulha.model.PassengerCar;
import com.shulha.model.Truck;
import com.shulha.service.CarService;
import com.shulha.util.RandomGenerator;

public class Main {
    public static void main(String[] args) {
        final CarService carService = new CarService();

        final PassengerCar passengerCar = carService.createPassengerCar();
        final Truck truck = carService.createTruck();

        truck.restore();
        passengerCar.restore();

        CarService.check(passengerCar);
        CarService.check(truck);

//        carService.printAll();

//        carService.print(passengerCar);
//        carService.print(truck);

//        carService.delete(passengerCar.getId());
//        carService.printAll();
        carService.insert(3, new Truck());
        carService.printAll();
    }
}
