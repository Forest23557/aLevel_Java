package com.shulha.repository;

import com.shulha.model.Car;
import com.shulha.model.Order;
import com.shulha.model.PassengerCar;
import com.shulha.model.Truck;
import com.shulha.util.ConnectionPool;
import lombok.SneakyThrows;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

public class OrderJdbcRepository implements Repository<Order, String> {
    private static OrderJdbcRepository instance;
    private final CarJdbcRepository carJdbcRepository;

    private OrderJdbcRepository(final CarJdbcRepository carJdbcRepository) {
        this.carJdbcRepository = carJdbcRepository;
        createTableInDB();
    }

    private static OrderJdbcRepository getInstance() {
        instance = Optional.ofNullable(instance)
                .orElseGet(() -> new OrderJdbcRepository(CarJdbcRepository.getInstance()));
        return instance;
    }

    @SneakyThrows
    private void createTableInDB() {
        ConnectionPool.createCurrentConnection();
        final Connection connection = ConnectionPool.getCurrentConnection();

        final String sqlRequest = "CREATE TABLE IF NOT EXISTS \"orders\"(" +
                "\"id\" varchar(36) NOT NULL PRIMARY KEY, " +
                "\"order_date_and_time\" TIMESTAMP NOT NULL" +
                ");";
        final PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);

        try {
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        carJdbcRepository.createTableInDB(connection);

        ConnectionPool.commitAndClose();
    }

    @SneakyThrows
    @Override
    public void delete(final String id) {
        if (Objects.nonNull(id) && !id.isBlank()) {
            ConnectionPool.createCurrentConnection();
            final Connection connection = ConnectionPool.getCurrentConnection();

            final String deleteEngines = "DELETE FROM orders WHERE id = ?;";

            final PreparedStatement preparedStatement = connection.prepareStatement(deleteEngines);
            preparedStatement.setString(1, id);

            try {
                preparedStatement.executeUpdate();
                preparedStatement.close();
                ConnectionPool.commitAndClose();
            } finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        }
    }

    @SneakyThrows
    @Override
    public void save(final Order order) {
        ConnectionPool.createCurrentConnection();
        final Connection connection = ConnectionPool.getCurrentConnection();
        final String orderId = order.getId();
        final Timestamp timestamp = Timestamp.valueOf(order.getDate());
        final Iterator<Car> carIterator = order.getAll().iterator();

        final int count = checkIfOrderExists(orderId, connection);

        if (count == 0) {
            final String sqlRequest = "INSERT INTO \"orders\" (\"id\", \"order_date_and_time\") VALUES (?, ?);";
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
            preparedStatement.setString(1, orderId);
            preparedStatement.setTimestamp(2, timestamp);

            try {
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }

            getAndSaveCar(orderId, carIterator, connection);

        } else {
            getAndSaveCar(orderId, carIterator, connection);
        }

        ConnectionPool.commitAndClose();
    }

    @SneakyThrows
    private int checkIfOrderExists(final String orderId, final Connection connection) {
        final String checkOrderExistenceSql = "SELECT COUNT(id) FROM \"cars\" WHERE cars.id = ?;";
        final PreparedStatement preparedStatement = connection.prepareStatement(checkOrderExistenceSql);

        preparedStatement.setString(1, orderId);

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

    private void getAndSaveCar(final String orderId, final Iterator<Car> carIterator, final Connection connection) {
        while (carIterator.hasNext()) {
            final Car nextCar = carIterator.next();
            carJdbcRepository.save(nextCar);

            carJdbcRepository.setOrderId(orderId, nextCar, connection);
        }
    }

    @SneakyThrows
    @Override
    public void removeAll() {
        ConnectionPool.createCurrentConnection();
        final Connection connection = ConnectionPool.getCurrentConnection();

        final String deleteEngines = "DELETE FROM orders;";

        final PreparedStatement preparedStatement = connection.prepareStatement(deleteEngines);

        try {
            preparedStatement.executeUpdate();
            preparedStatement.close();
            ConnectionPool.commitAndClose();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @SneakyThrows
    @Override
    public List<Order> getAll() {
        ConnectionPool.createCurrentConnection();
        final Connection connection = ConnectionPool.getCurrentConnection();
        final List<Order> orderList;
        final Map<String, LocalDateTime> orderDataMap;
        final Map<String, String> carOrderIdMap = new HashMap<>();

        final String sqlRequest = "SELECT orders.id AS \"order_id\", orders.order_date_and_time, " +
                "cars.id AS \"car_id\" " +
                "FROM \"orders\" " +
                "LEFT JOIN \"cars\" ON orders.id = cars.order_id;";
        final PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
        final ResultSet resultSet = preparedStatement.executeQuery();

        orderDataMap = dbDataToMap(resultSet, carOrderIdMap);

        orderList = mapToOrder(orderDataMap, carOrderIdMap);

        try {
            resultSet.close();
            preparedStatement.close();
            ConnectionPool.shutDownCurrentConnection();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return orderList;
    }

    @SneakyThrows
    private List<Order> mapToOrder(final Map<String, LocalDateTime> orderDataMap,
                                   final Map<String, String> carOrderIdMap) {

        final List<Order> orderList = new ArrayList<>();
        final Iterator<String> iterator = orderDataMap.keySet().iterator();
        final Class<Order> orderClass = Order.class;
        final Field fieldId = orderClass.getDeclaredField("id");
        final Field date = orderClass.getDeclaredField("date");
        final Map<String, Car> carMap;

        fieldId.setAccessible(true);
        date.setAccessible(true);

        carMap = carJdbcRepository.carListToMap(carJdbcRepository.getAll());

        while (iterator.hasNext()) {
            final String orderId = iterator.next();
            final LocalDateTime orderDateAndTime = orderDataMap.get(orderId);
            final Order order = new Order();

            fieldId.set(order, orderId);
            date.set(order, orderDateAndTime);

            carOrderIdMap.entrySet()
                    .stream()
                    .filter(entry -> entry.getValue().equals(orderId))
                    .map(Map.Entry::getKey)
                    .forEach(carId -> {
                        final Car car = carMap.get(carId);
                        order.save(car);
                    });

            orderList.add(order);
        }

        return orderList;
    }

    @SneakyThrows
    private Map<String, LocalDateTime> dbDataToMap(final ResultSet resultSet, final Map<String, String> carOrderIdMap) {
        final Map<String, LocalDateTime> orderDataMap = new HashMap<>();

        while (resultSet.next()) {
            final String orderId = resultSet.getString("order_id");
            final LocalDateTime orderDateAndTime = resultSet.getTimestamp("order_date_and_time")
                    .toLocalDateTime();
            final String carId = resultSet.getString("car_id");
            orderDataMap.putIfAbsent(orderId, orderDateAndTime);

            if (Objects.nonNull(carId)) {
                carOrderIdMap.putIfAbsent(carId, orderId);
            }
        }

        return orderDataMap;
    }

    @SneakyThrows
    @Override
    public Optional<Order> getById(final String id) {
        Order order = null;

        if (Objects.nonNull(id) && !id.isBlank()) {
            final List<Order> orderList;
            final Map<String, LocalDateTime> orderDataMap;
            final Map<String, String> carOrderIdMap = new HashMap<>();
            ConnectionPool.createCurrentConnection();
            final Connection connection = ConnectionPool.getCurrentConnection();

            final String sqlRequest = "SELECT orders.id AS \"order_id\", orders.order_date_and_time, " +
                    "cars.id AS \"car_id\" " +
                    "FROM \"orders\" " +
                    "JOIN \"cars\" ON orders.id = cars.order_id " +
                    "WHERE orders.id = ?;";
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
            preparedStatement.setString(1, id);

            final ResultSet resultSet = preparedStatement.executeQuery();

            orderDataMap = dbDataToMap(resultSet, carOrderIdMap);

            orderList = mapToOrder(orderDataMap, carOrderIdMap);

            if (orderList.size() == 1) {
                order = orderList.get(0);
            }

            try {
                resultSet.close();
                preparedStatement.close();
                ConnectionPool.shutDownCurrentConnection();
            } finally {
                if (resultSet != null) {
                    resultSet.close();
                }
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        }

        return Optional.ofNullable(order);
    }

//    @SneakyThrows
//    public static void main(String[] args) {
//        final OrderJdbcRepository instance1 = OrderJdbcRepository.getInstance();
//        final Order order = new Order();
//        order.save(new Truck());
//        order.save(new PassengerCar());
//        instance1.save(order);
//
//        final List<Order> orderList = instance1.getAll();
//        final String id = orderList.get(1).getId();
//        System.out.println("ID: " + id);
//        System.out.println(orderList);
//        System.out.println("---".repeat(20));
//        instance1.removeAll();
//        instance1.delete(id);
//        System.out.println(instance1.getAll());
//        System.out.println(instance1.getById(id));
//    }
}
