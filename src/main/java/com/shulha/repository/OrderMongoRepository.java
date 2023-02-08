package com.shulha.repository;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.shulha.annotation.Singleton;
import com.shulha.config.MongoUtil;
import com.shulha.model.*;
import com.shulha.util.CarGsonDeserializer;
import com.shulha.util.OrderGsonDeserializer;
import com.shulha.util.RandomGenerator;
import org.bson.Document;

import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class OrderMongoRepository implements Repository<Order, String> {
    private static MongoDatabase mongoDatabase = MongoUtil.connect("mongodb");
    private static MongoCollection<Document> mongoDatabaseCollectionOrders = mongoDatabase.getCollection("order");
    private static OrderMongoRepository instance;
    private final CarMongoRepository carMongoRepository;

    private OrderMongoRepository(final CarMongoRepository carMongoRepository) {
        this.carMongoRepository = carMongoRepository;
    }

    public static OrderMongoRepository getInstance() {
        instance = Optional.ofNullable(instance)
                .orElseGet(() -> new OrderMongoRepository(CarMongoRepository.getInstance()));
        return instance;
    }

    private String orderToJson(final Order order) {
        final JsonObject jsonObject = new JsonObject();
        final String date = order.getDate()
                .toString();

        jsonObject.addProperty("id", order.getId());
        jsonObject.addProperty("date", date);

        return jsonObject.toString();
    }

    private void update(final Order order) {
        final String date = order.getDate()
                .toString();

        final Document filter = new Document();
        filter.append("id", order.getId());

        final Document newData = new Document();
        newData.append("date", date);

        final Document updateOrder = new Document();
        updateOrder.append("$set", newData);

        mongoDatabaseCollectionOrders.updateOne(filter, updateOrder);
    }

    private Order jsonToOrder(final String json) {
        final Gson gson = new GsonBuilder()
                .registerTypeAdapter(Order.class, new OrderGsonDeserializer())
                .create();
        final Order order = gson.fromJson(json, Order.class);
        final List<Car> carsByOrderId = carMongoRepository.findCarsByOrderId(order.getId());

        carsByOrderId.stream()
                .forEach(car ->
                        order.getCars()
                                .add(car));

        return order;
    }

    @Override
    public void delete(final String id) {
        if (Objects.nonNull(id) && !id.isBlank()) {
            final Document filter = new Document();
            filter.append("id", id);

            getById(id)
                    .filter(Objects::nonNull)
                    .ifPresent(order -> {
                        order.getCars()
                                .stream()
                                .dropWhile(Objects::isNull)
                                .map(car -> car.getId())
                                .forEach(carId -> carMongoRepository.delete(carId));
                    });

            mongoDatabaseCollectionOrders.findOneAndDelete(filter);
        }
    }

    @Override
    public void save(final Order order) {
        if (Objects.nonNull(order)) {
            final String id = order.getId();

            getById(id).ifPresentOrElse(
                    order1 -> {
                        update(order);
                    },
                    () -> {
                        final String serializedOrder = orderToJson(order);
                        final Document carDocument = Document.parse(serializedOrder);

                        mongoDatabaseCollectionOrders.insertOne(carDocument);
                    }
            );

            order.getCars()
                    .stream()
                    .forEach(car -> {
                        car.setOrderId(order.getId());
                        carMongoRepository.save(car);
                    });
        }
    }

    @Override
    public void removeAll() {
        mongoDatabaseCollectionOrders.drop();
        carMongoRepository.removeAll();
    }

    @Override
    public List<Order> getAll() {
        final List<Order> orderList = new ArrayList<>();
        final FindIterable<Document> documentFindIterable =
                mongoDatabaseCollectionOrders.find();

        for (final Document document : documentFindIterable) {
            final String json = document.toJson();
            final Order order = jsonToOrder(json);
            orderList.add(order);
        }

        return orderList;
    }

    @Override
    public Optional<Order> getById(final String id) {
        Order order = null;

        if (Objects.nonNull(id) && !id.isBlank()) {
            final Document filter = new Document();
            filter.append("id", id);
            final FindIterable<Document> documentFindIterable =
                    mongoDatabaseCollectionOrders.find(filter);

            for (final Document document : documentFindIterable) {
                final String json = document.toJson();
                order = jsonToOrder(json);
            }
        }

        return Optional.ofNullable(order);
    }

//    public static void main(String[] args) {
//        final OrderMongoRepository orderMongoRepository = OrderMongoRepository.getInstance();
//
//        orderMongoRepository.removeAll();
//
//        for (int i = 0; i < 10; i++) {
//            final Random random = new Random();
//            final int randomPower = random.nextInt(751) + 250;
//            final int randomPower1 = random.nextInt(751) + 250;
//
//            final Order order = new Order();
//
//            final PassengerCar passengerCar = new PassengerCar();
//            passengerCar.setPassengerCount(2);
//            passengerCar.setCount(5);
//            passengerCar.setEngine(new Engine(randomPower, EngineTypes.MPFI));
//
//            final String id = order.getId();
//            System.out.println(id);
//
//            final Truck truck = new Truck();
//            truck.setLoadCapacity(randomPower);
//            truck.setEngine(new Engine(randomPower1, EngineTypes.CRDI));
//
//            order.getCars().add(passengerCar);
//            order.getCars().add(truck);
//
//            orderMongoRepository.save(order);
//            System.out.println(orderMongoRepository.getById(id));
//
//            if (i < 5) {
//                orderMongoRepository.delete(id);
//            }
//        }
//
//        System.out.println(orderMongoRepository.getAll());
//    }
}
