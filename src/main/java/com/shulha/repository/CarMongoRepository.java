package com.shulha.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.shulha.config.MongoUtil;
import com.shulha.model.*;
import com.shulha.util.CarGsonDeserializer;
import org.bson.Document;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarMongoRepository implements Repository<Car, String> {
    private static MongoDatabase mongoDatabase = MongoUtil.connect("mongodb");
    private static MongoCollection<Document> mongoDatabaseCollectionCars = mongoDatabase.getCollection("car");
    private static CarMongoRepository instance;
    private final EngineMongoRepository engineMongoRepository;

    private CarMongoRepository(final EngineMongoRepository engineMongoRepository) {
        this.engineMongoRepository = engineMongoRepository;
    }

    public static CarMongoRepository getInstance() {
        instance = Optional.ofNullable(instance)
                .orElseGet(() -> new CarMongoRepository(EngineMongoRepository.getInstance()));
        return instance;
    }

    private String carToJson(final Car car) {
        final Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .excludeFieldsWithoutExposeAnnotation()
                .serializeNulls()
                .create();

        return gson.toJson(car);
    }

    private void update(final Car car) {
        final CarTypes carType = car.getType();

        final Document filter = new Document();
        filter.append("id", car.getId());

        final Document newData = new Document();
        newData.append("type", carType.toString());
        newData.append("manufacturer", car.getManufacturer().toString());
        newData.append("color", car.getColor().toString());
        newData.append("count", car.getCount());
        newData.append("price", car.getPrice());

        if (carType.equals(CarTypes.CAR)) {
            final PassengerCar passengerCar = (PassengerCar) car;
            newData.append("passengerCount", passengerCar.getPassengerCount());
        } else {
            final Truck truck = (Truck) car;
            newData.append("loadCapacity", truck.getLoadCapacity());
        }

        newData.append("engineId", car.getEngine().getId());

        final Document updateEngine = new Document();
        updateEngine.append("$set", newData);

        mongoDatabaseCollectionCars.updateOne(filter, updateEngine);
    }

    private Car jsonToCar(final String json) {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Car.class, new CarGsonDeserializer())
                .create();
        final Car car = gson.fromJson(json, Car.class);
        final String engineId = car.getEngine().getId();
        engineMongoRepository.getById(engineId)
                .ifPresent(engine -> car.setEngine(engine));

        return car;
    }

    @Override
    public void delete(final String id) {
        if (Objects.nonNull(id) && !id.isBlank()) {
            final Document filter = new Document();
            filter.append("id", id);

            getById(id)
                    .filter(Objects::nonNull)
                    .map(car -> car.getEngine().getId())
                    .ifPresent(engineId -> engineMongoRepository.delete(engineId));

            mongoDatabaseCollectionCars.findOneAndDelete(filter);
        }
    }

    @Override
    public void save(final Car car) {
        if (Objects.nonNull(car)) {
            final String id = car.getId();

            getById(id).ifPresentOrElse(
                    car1 -> {
                        update(car);
                    },
                    () -> {
                        final String serializedEngine = carToJson(car);
                        final Document carDocument = Document.parse(serializedEngine);
                        carDocument.append("engineId", car.getEngine().getId());

                        mongoDatabaseCollectionCars.insertOne(carDocument);
                    }
            );

            engineMongoRepository.save(car.getEngine());
        }
    }

    @Override
    public void removeAll() {
        mongoDatabaseCollectionCars.drop();
        engineMongoRepository.removeAll();
    }

    @Override
    public List<Car> getAll() {
        final List<Car> carList = new ArrayList<>();
        final FindIterable<Document> documentFindIterable = mongoDatabaseCollectionCars.find();

        for (final Document document : documentFindIterable) {
            final String json = document.toJson();
            final Car car = jsonToCar(json);
            carList.add(car);
        }

        return carList;
    }

    @Override
    public Optional<Car> getById(final String id) {
        Car car = null;

        if (Objects.nonNull(id) && !id.isBlank()) {
            final Document filter = new Document();
            filter.append("id", id);
            final FindIterable<Document> documentFindIterable =
                    mongoDatabaseCollectionCars.find(filter);

            for (final Document document : documentFindIterable) {
                final String json = document.toJson();
                car = jsonToCar(json);
            }
        }

        return Optional.ofNullable(car);
    }

    public static void main(String[] args) {
        final CarMongoRepository carMongoRepository = CarMongoRepository.getInstance();
        final PassengerCar passengerCar = new PassengerCar();
        passengerCar.setEngine(new Engine(456, EngineTypes.CRDI));
        final Truck truck = new Truck();
        truck.setEngine(new Engine(380, EngineTypes.MPFI));
        final String id = passengerCar.getId();
        System.out.println(id);

        carMongoRepository.removeAll();
        carMongoRepository.save(passengerCar);
        passengerCar.setPassengerCount(2);
        passengerCar.setCount(5);
        passengerCar.setEngine(new Engine(820, EngineTypes.TURBOCHARGED));
        carMongoRepository.save(passengerCar);
        carMongoRepository.save(truck);
//        System.out.println(carMongoRepository.getAll());
//        System.out.println(carMongoRepository.getById(id));
//        carMongoRepository.delete(id);
        System.out.println(carMongoRepository.getAll());
    }
}
