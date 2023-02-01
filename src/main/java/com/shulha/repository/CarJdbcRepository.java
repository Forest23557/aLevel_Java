package com.shulha.repository;

import com.shulha.annotation.Singleton;
import com.shulha.model.*;
import com.shulha.util.ConnectionPool;
import com.shulha.util.JdbcManager;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Singleton
public class CarJdbcRepository implements Repository<Car, String> {
    private static CarJdbcRepository instance;
    private final EngineJdbcRepository engineJdbcRepository;

    private CarJdbcRepository(final EngineJdbcRepository engineJdbcRepository) {
        this.engineJdbcRepository = engineJdbcRepository;
    }

    public static CarJdbcRepository getInstance() {
        instance = Optional.ofNullable(instance)
                .orElseGet(() -> new CarJdbcRepository(EngineJdbcRepository.getInstance()));
        return instance;
    }

    @SneakyThrows
    protected void createTableInDB(final Connection connection) {
        final String createTrucksTableRequest = "CREATE TABLE IF NOT EXISTS \"trucks\"(" +
                "\"id\" VARCHAR(36) NOT NULL PRIMARY KEY, " +
                "\"load_capacity\" INTEGER NOT NULL, " +
                "\"car_id\" VARCHAR(36) NOT NULL, " +
                "FOREIGN KEY (\"car_id\") REFERENCES \"cars\"(\"id\")" +
                ");";
        final String createPassengerCarsTableRequest = "CREATE TABLE IF NOT EXISTS \"passenger_cars\"(" +
                "\"id\" VARCHAR(36) NOT NULL PRIMARY KEY, " +
                "\"passenger_count\" INTEGER NOT NULL, " +
                "\"car_id\" VARCHAR(36) NOT NULL, " +
                "FOREIGN KEY (\"car_id\") REFERENCES \"cars\"(\"id\")" +
                ");";
        final String createCarsTableRequest = "CREATE TABLE IF NOT EXISTS \"cars\"(" +
                "\"id\" VARCHAR(36) NOT NULL PRIMARY KEY, " +
                "\"type\" VARCHAR(50) NOT NULL, " +
                "\"manufacturer\" VARCHAR(50) NOT NULL, " +
                "\"color\" VARCHAR(50) NOT NULL, " +
                "\"count\" INTEGER NOT NULL, " +
                "\"price\" INTEGER NOT NULL, " +
                "\"engine_id\" VARCHAR(36), " +
                "\"order_id\" VARCHAR(36), " +
                "FOREIGN KEY (\"engine_id\") REFERENCES \"engines\"(\"id\") ON DELETE SET NULL, " +
                "FOREIGN KEY (\"order_id\") REFERENCES \"orders\"(\"id\") ON DELETE SET NULL" +
                ");";

        final Statement statement = connection.createStatement();

        statement.addBatch(createCarsTableRequest);
        statement.addBatch(createTrucksTableRequest);
        statement.addBatch(createPassengerCarsTableRequest);

        try {
            statement.executeBatch();
            statement.close();
        } finally {
            if (statement != null) {
                statement.close();
            }
        }
    }

    @SneakyThrows
    @Override
    public void delete(final String id) {
        if (Objects.nonNull(id) && !id.isBlank()) {
            final AtomicBoolean isConnectionCreated = new AtomicBoolean(false);
            final Connection connection = ConnectionPool.checkAndGetConnection(isConnectionCreated);

            final String deleteCarById = "DELETE FROM passenger_cars WHERE car_id = ?; " +
                    "DELETE FROM trucks WHERE car_id = ?; " +
                    "DELETE FROM cars WHERE id = ?;";

            final PreparedStatement preparedStatement = connection.prepareStatement(deleteCarById);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, id);
            preparedStatement.setString(3, id);

            try {
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null && isConnectionCreated.get()) {
                    ConnectionPool.commitAndClose();
                }
            }
        }
    }

    @SneakyThrows
    @Override
    public void save(final Car car) {
        final AtomicBoolean isConnectionCreated = new AtomicBoolean(false);
        final Engine engine = car.getEngine();
        final String carId = car.getId();

        final Connection connection = ConnectionPool.checkAndGetConnection(isConnectionCreated);

        engineJdbcRepository.save(engine);

        int count = checkIfCarExists(carId, connection);

        if (count == 0) {
            final String insertCar = "INSERT INTO \"cars\" (\"id\", \"type\", \"manufacturer\", \"color\", \"count\", " +
                    "\"price\", \"engine_id\") VALUES (?, ?, ?, ?, ?, ?, ?);";
            final PreparedStatement preparedStatement = connection.prepareStatement(insertCar);

            preparedStatement.setString(1, carId);
            preparedStatement.setString(2, car.getType().toString());
            preparedStatement.setString(3, car.getManufacturer().toString());
            preparedStatement.setString(4, car.getColor().toString());
            preparedStatement.setInt(5, car.getCount());
            preparedStatement.setInt(6, car.getPrice());
            preparedStatement.setString(7, engine.getId());

            try {
                preparedStatement.executeUpdate();
            } finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }

            saveCarFeaturesByType(car, connection);
        }

        if (connection != null && isConnectionCreated.get()) {
            ConnectionPool.commitAndClose();
        }
    }

//    @SneakyThrows
//    private int getCarCount(final String carId) {
//        final String getCount = "SELECT count FROM \"cars\" WHERE id = ?;";
//        final PreparedStatement preparedStatement = connection.prepareStatement(getCount);
//
//        preparedStatement.setString(1, carId);
//
//        final ResultSet resultSet = preparedStatement.executeQuery();
//
//        resultSet.next();
//
//        final int count = resultSet.getInt("count");
//
//        try {
//            resultSet.close();
//            preparedStatement.close();
//        } finally {
//            if (preparedStatement != null) {
//                preparedStatement.close();
//            }
//        }
//
//        return count;
//    }

//    @SneakyThrows
//    private void updateCarCount(final int count, final String carId) {
//        final String updateCount = "UPDATE \"cars\" SET count = ? WHERE id = ?;";
//        final PreparedStatement preparedStatement = connection.prepareStatement(updateCount);
//
//        preparedStatement.setInt(1, count);
//        preparedStatement.setString(2, carId);
//
//        try {
//            preparedStatement.executeUpdate();
//            connection.commit();
//            preparedStatement.close();
//        } finally {
//            if (preparedStatement != null) {
//                preparedStatement.close();
//            }
//        }
//    }

    @SneakyThrows
    private int checkIfCarExists(final String carId, final Connection connection) {
        final String checkCarExistenceSql = "SELECT COUNT(id) FROM \"cars\" WHERE cars.id = ?;";
        final PreparedStatement preparedStatement = connection.prepareStatement(checkCarExistenceSql);

        preparedStatement.setString(1, carId);

        final ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        final int count = resultSet.getInt("count");

        try {
            preparedStatement.close();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return count;
    }

    @SneakyThrows
    private void saveCarFeaturesByType(final Car car, final Connection connection) {
        final PreparedStatement preparedStatement;

        if (car.getType() == CarTypes.CAR) {
            final PassengerCar passengerCar = (PassengerCar) car;
            final String insertPassengerCar = "INSERT INTO \"passenger_cars\" (\"passenger_count\", \"car_id\", \"id\") " +
                    "VALUES (?, ?, ?);";
            preparedStatement = connection.prepareStatement(insertPassengerCar);
            preparedStatement.setInt(1, passengerCar.getPassengerCount());
        } else {
            final Truck truck = (Truck) car;
            final String insertPassengerCar = "INSERT INTO \"trucks\" (\"load_capacity\", \"car_id\", \"id\") " +
                    "VALUES (?, ?, ?);";
            preparedStatement = connection.prepareStatement(insertPassengerCar);
            preparedStatement.setInt(1, truck.getLoadCapacity());
        }

        preparedStatement.setString(2, car.getId());
        preparedStatement.setString(3, car.getId());

        try {
            preparedStatement.executeUpdate();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @SneakyThrows
    protected void setOrderId(final String orderId, final Car nextCar, final Connection connection) {
        final String setOrderIdSql = "UPDATE \"cars\" SET \"order_id\" = ? WHERE \"id\" = ?;";
        final PreparedStatement preparedStatement = connection.prepareStatement(setOrderIdSql);

        preparedStatement.setString(1, orderId);
        preparedStatement.setString(2, nextCar.getId());

        try {
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @SneakyThrows
    @Override
    public void removeAll() {
        final AtomicBoolean isConnectionCreated = new AtomicBoolean(false);
        final Connection connection = ConnectionPool.checkAndGetConnection(isConnectionCreated);

        final String deletePassengerCarParameters = "DELETE FROM passenger_cars;";
        final String deleteTruckParameters = "DELETE FROM trucks;";
        final String deleteCars = "DELETE FROM cars;";

        final Statement statement = connection.createStatement();
        statement.addBatch(deletePassengerCarParameters);
        statement.addBatch(deleteTruckParameters);
        statement.addBatch(deleteCars);

        try {
            statement.executeBatch();
            statement.close();
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null && isConnectionCreated.get()) {
                ConnectionPool.commitAndClose();
            }
        }
    }

    @SneakyThrows
    @Override
    public List<Car> getAll() {
        final List<Car> carList;
        final AtomicBoolean isConnectionCreated = new AtomicBoolean(false);

        final Connection connection = ConnectionPool.checkAndGetConnection(isConnectionCreated);

        final String sqlRequest = "SELECT cars.id AS \"car_id\", " +
                "cars.type AS \"car_type\", " +
                "cars.manufacturer AS \"car_manufacturer\", cars.color AS \"car_color\", " +
                "cars.count AS \"car_count\", cars.price AS \"car_price\", pc.passenger_count, " +
                "trucks.load_capacity, engines.id AS \"engine_id\" " +
                "FROM \"cars\" " +
                "LEFT JOIN \"engines\" ON cars.engine_id = engines.id " +
                "LEFT JOIN \"passenger_cars\" AS \"pc\" ON cars.id = pc.car_id " +
                "LEFT JOIN \"trucks\" ON cars.id = trucks.car_id;";
        final PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
        final ResultSet resultSet = preparedStatement.executeQuery();
        final Map<String, List<Object>> carStringMap = dbDataToStringMap(resultSet);

        carList = mapToCar(carStringMap);

        try {
            resultSet.close();
            preparedStatement.close();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null && isConnectionCreated.get()) {
                ConnectionPool.shutDownCurrentConnection();
            }
        }

        return carList;
    }

    @SneakyThrows
    protected Car getCarFromMapById(final String carId, final Map<String, List<Object>> carStringMap) {
        final Class<Car> carClass = Car.class;
        final Field fieldId = carClass.getDeclaredField("id");

        fieldId.setAccessible(true);

        final Car car;
        final List<Object> carParametersList = carStringMap.get(carId);
        final String engineId = (String) carParametersList.get(0);
        final CarTypes carType =
                Enum.valueOf(CarTypes.class, (String) carParametersList.get(1));
        final CarManufacturers carManufacturer =
                Enum.valueOf(CarManufacturers.class, (String) carParametersList.get(2));
        final CarColors carColor =
                Enum.valueOf(CarColors.class, (String) carParametersList.get(3));
        final int carCount = (int) carParametersList.get(4);
        final int carPrice = (int) carParametersList.get(5);

        final Engine engine = engineJdbcRepository.getById(engineId)
                .orElseGet(Engine::new);

        if (carType.equals(CarTypes.CAR)) {
            final int passengerCount = (int) carParametersList.get(6);
            final PassengerCar passengerCar = new PassengerCar(carManufacturer, engine, carColor, passengerCount);
            car = passengerCar;
        } else {
            final int loadCapacity = (int) carParametersList.get(6);
            final Truck truck = new Truck(carManufacturer, engine, carColor, loadCapacity);
            car = truck;
        }

        car.setCount(carCount);
        car.setPrice(carPrice);
        fieldId.set(car, carId);

        return car;
    }

    @SneakyThrows
    protected List<Car> mapToCar(final Map<String, List<Object>> carStringMap) {
        final List<Car> carList = new ArrayList<>();
        final Iterator<String> iterator = carStringMap.keySet().iterator();
        final Class<Car> carClass = Car.class;
        final Field fieldId = carClass.getDeclaredField("id");

        fieldId.setAccessible(true);

        while (iterator.hasNext()) {
            final Car car;
            final String carId = iterator.next();
            final List<Object> carParametersList = carStringMap.get(carId);
            final String engineId = (String) carParametersList.get(0);
            final CarTypes carType =
                    Enum.valueOf(CarTypes.class, (String) carParametersList.get(1));
            final CarManufacturers carManufacturer =
                    Enum.valueOf(CarManufacturers.class, (String) carParametersList.get(2));
            final CarColors carColor =
                    Enum.valueOf(CarColors.class, (String) carParametersList.get(3));
            final int carCount = (int) carParametersList.get(4);
            final int carPrice = (int) carParametersList.get(5);

            final Engine engine = engineJdbcRepository.getById(engineId)
                    .orElseGet(Engine::new);

            if (carType.equals(CarTypes.CAR)) {
                final int passengerCount = (int) carParametersList.get(6);
                final PassengerCar passengerCar = new PassengerCar(carManufacturer, engine, carColor, passengerCount);
                car = passengerCar;
            } else {
                final int loadCapacity = (int) carParametersList.get(6);
                final Truck truck = new Truck(carManufacturer, engine, carColor, loadCapacity);
                car = truck;
            }

            car.setCount(carCount);
            car.setPrice(carPrice);
            fieldId.set(car, carId);
            carList.add(car);
        }

        return carList;
    }

    @SneakyThrows
    protected Map<String, List<Object>> dbDataToStringMap(final ResultSet resultSet) {
        final Map<String, List<Object>> carStringsMap = new HashMap<>();

        while (resultSet.next()) {
            final String carId = resultSet.getString("car_id");

            if (Objects.isNull(carId)) continue;

            final List<Object> carParametersList = new ArrayList<>();
            final String engineId = resultSet.getString("engine_id");
            final String type = resultSet.getString("car_type");
            final String carManufacturer = resultSet.getString("car_manufacturer");
            final String carColor = resultSet.getString("car_color");
            final Integer carCount = resultSet.getInt("car_count");
            final Integer carPrice = resultSet.getInt("car_price");

            carParametersList.add(engineId);
            carParametersList.add(type);
            carParametersList.add(carManufacturer);
            carParametersList.add(carColor);
            carParametersList.add(carCount);
            carParametersList.add(carPrice);

            if (Enum.valueOf(CarTypes.class, type).equals(CarTypes.CAR)) {
                final int passengerCount = resultSet.getInt("passenger_count");
                carParametersList.add(passengerCount);
            } else {
                final int loadCapacity = resultSet.getInt("load_capacity");
                carParametersList.add(loadCapacity);
            }

            carStringsMap.put(carId, carParametersList);
        }

        return carStringsMap;
    }

    @SneakyThrows
    @Override
    public Optional<Car> getById(final String id) {
        Car car = null;

        if (Objects.nonNull(id) && !id.isBlank()) {
            final List<Car> carList;
            final AtomicBoolean isConnectionCreated = new AtomicBoolean(false);

            final Connection connection = ConnectionPool.checkAndGetConnection(isConnectionCreated);

            final String sqlRequest = "SELECT cars.id AS \"car_id\", " +
                    "cars.type AS \"car_type\", " +
                    "cars.manufacturer AS \"car_manufacturer\", cars.color AS \"car_color\", " +
                    "cars.count AS \"car_count\", cars.price AS \"car_price\", pc.passenger_count, " +
                    "trucks.load_capacity, engines.id AS \"engine_id\" " +
                    "FROM \"cars\" " +
                    "LEFT JOIN \"engines\" ON cars.engine_id = engines.id " +
                    "LEFT JOIN \"passenger_cars\" AS \"pc\" ON cars.id = pc.car_id " +
                    "LEFT JOIN \"trucks\" ON cars.id = trucks.car_id " +
                    "WHERE cars.id = ?;";
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);

            preparedStatement.setString(1, id);

            final ResultSet resultSet = preparedStatement.executeQuery();
            final Map<String, List<Object>> carStringMap = dbDataToStringMap(resultSet);

            carList = mapToCar(carStringMap);

            if (carList.size() == 1) {
                car = carList.get(0);
            }

            try {
                resultSet.close();
                preparedStatement.close();
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null && isConnectionCreated.get()) {
                    ConnectionPool.shutDownCurrentConnection();
                }
            }
        }

        return Optional.ofNullable(car);
    }

//    public static void main(String[] args) {
//        final CarJdbcRepository instance1 = CarJdbcRepository.getInstance();
//        final List<Car> carList = instance1.getAll();
//        final String id = carList.get(0).getId();
//        System.out.println("ID: " + id);
//        System.out.println(carList);
//        System.out.println("---".repeat(20));
//        instance1.delete(id);
//        instance1.removeAll();
//        System.out.println(instance1.getAll());
//        System.out.println(instance1.getById(id));
//    }
}
