package com.shulha.service;

import com.shulha.container.CarComparator;
import com.shulha.model.*;
import com.shulha.repository.CarArrayRepository;
import com.shulha.util.RandomGenerator;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static java.util.function.Function.*;

public class CarService {
    private final static Random RANDOM = new Random();

    private final CarArrayRepository carArrayRepository;
    private static CarService instance;

    private CarService(final CarArrayRepository carArrayRepository) {
        this.carArrayRepository = carArrayRepository;
    }

    public static CarService getInstance() {
        instance = Optional
                .ofNullable(instance)
                .orElseGet(() -> new CarService(CarArrayRepository.getInstance()));
        return instance;
    }

    public static CarService getInstance(final CarArrayRepository repository) {
        instance = Optional
                .ofNullable(instance)
                .orElseGet(() -> new CarService(Optional
                        .ofNullable(repository)
                        .orElseGet(() -> CarArrayRepository.getInstance())));
        return instance;
    }

    public Car carXmlToCarObject(final String path) {
        final Map<String, Object> mapCar = new LinkedHashMap<>();

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try(final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(classLoader.getResourceAsStream(path)))) {

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                final Pattern pattern1 = Pattern.compile("(?<=<)\\w*");
                final Matcher matcher1 = pattern1.matcher(line);
                final Pattern pattern2 = Pattern.compile("(?<=>)\\w*");
                final Matcher matcher2 = pattern2.matcher(line);
                String key = "";
                String value = "";


                if (matcher1.find()) {
                    key = matcher1.group();
                }

                if (matcher2.find()) {
                    value = matcher2.group();
                }

                if (!key.isBlank() && !value.isBlank()) {
                    mapCar.put(key, value);
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return mapToObject(mapCar);
    }

    public Car carJsonToCarObject(final String path) {
        final Map<String, Object> mapCar = new LinkedHashMap<>();

        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        try (final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(classLoader.getResourceAsStream(path)))) {

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                final Pattern pattern = Pattern.compile("(?<=\")\\w+");
                final Matcher matcher = pattern.matcher(line);
                String key = "";
                String value = "";
                int i = 0;

                while (matcher.find()) {
                    if (i == 0) {
                        key = matcher.group();
                    } else {
                        value = matcher.group();
                    }
                    i++;
                }

                if (!key.isBlank() && !value.isBlank()) {
                    mapCar.put(key, value);
                }
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        return mapToObject(mapCar);
    }

    public <T extends Car> void findManufacturerByPrice(final T[] cars, final int lowerBoundOfPrice) {
        Optional.ofNullable(cars)
                .orElseThrow(NullPointerException::new);
        if (lowerBoundOfPrice <= 0) {
            throw new IndexOutOfBoundsException("Your bound is less than or equal to 0!");
        }

        final Predicate<Car> pricePredicate = car -> car.getPrice() > lowerBoundOfPrice;

        System.out.printf("Manufacturers whose cars cost more than %d$: %n", lowerBoundOfPrice);
        Arrays.asList(cars)
                .stream()
                .filter(pricePredicate)
                .map(car -> car.getManufacturer())
                .forEach(System.out::println);
    }

    public <T extends Car> int countSum(final T[] cars) {
        Optional.ofNullable(cars)
                .orElseThrow(NullPointerException::new);

        final Integer carCount = Arrays.asList(cars)
                .stream()
                .map(car -> car.getCount())
                .reduce(0, (sum, next) -> sum + next);

        return carCount;
    }

    public <T extends Car> Map<String, CarTypes> mapToMap(final T[] cars) {
        Optional.ofNullable(cars)
                .orElseThrow(NullPointerException::new);

        final Comparator<T> comparator = (firstCar, secondCar) -> firstCar.getManufacturer()
                .compareTo(secondCar.getManufacturer());

        Map<String, CarTypes> sortedCarMap = Arrays.asList(cars)
                .stream()
                .sorted(comparator)
                .distinct()
                .peek(System.out::println)
                .collect(Collectors.toMap(Car::getId, Car::getType, (first, second) -> first, LinkedHashMap::new));

        return sortedCarMap;
    }

    public <T extends Car> IntSummaryStatistics statistic(final T[] cars) {
        Optional.ofNullable(cars)
                .orElseThrow(NullPointerException::new);

        final IntSummaryStatistics statistics = Arrays.asList(cars)
                .stream()
                .mapToInt(Car::getPrice)
                .summaryStatistics();

        System.out.printf("Price statistics: %nCount: %d%nSum: %d%nMin: %d%nAverage: %,.2f%nMax: %d%n",
                statistics.getCount(), statistics.getSum(), statistics.getMin(), statistics.getAverage(),
                statistics.getMax());

        return statistics;
    }

    public <T extends Car> boolean checkPrice(final T[] cars, final int lowerBoundOfPrice) {
        Optional.ofNullable(cars)
                .orElseThrow(NullPointerException::new);
        if (lowerBoundOfPrice <= 0) {
            throw new IndexOutOfBoundsException("Your bound is less than or equal to 0!");
        }

        final Predicate<Car> pricePredicate = car -> car.getPrice() > lowerBoundOfPrice;

        final boolean answer = Arrays.asList(cars)
                .stream()
                .allMatch(pricePredicate);

        System.out.printf("Are all the car prices more than %d$? - %b%n", lowerBoundOfPrice, answer);

        return answer;
    }

    public Car mapToObject(final Map<String, Object> carLinesMap) {
        Optional.ofNullable(carLinesMap)
                .orElseThrow(NullPointerException::new);

        final Function<Map, Car> changingFunction = map -> {
            final CarTypes carType = Enum.valueOf(CarTypes.class, (String) map.get("type"));
            final Engine engine = new Engine(Integer.parseInt((String) carLinesMap.get("power")),
                    Enum.valueOf(EngineTypes.class, (String) carLinesMap.get("engineType")));
            Car car;

            if (carType == CarTypes.CAR) {
                car = createCar(map);
            } else {
                car = createTruck(map);
            }

            car.setManufacturer(Enum.valueOf(CarManufacturers.class,
                    (String) carLinesMap.get("manufacturer")));
            car.setEngine(engine);
            car.setColor(Enum.valueOf(CarColors.class, (String) carLinesMap.get("color")));
            car.setPrice(Integer.parseInt((String) carLinesMap.get("price")));
            car.setCount(Integer.parseInt((String) carLinesMap.get("count")));

            return car;
        };

        final Car newCar = changingFunction.apply(carLinesMap);

        return newCar;
    }

    private Car createCar(final Map<String, Object> carLinesMap) {
        final PassengerCar car = new PassengerCar();
        car.setPassengerCount(Integer.parseInt(
                (String) carLinesMap.get("passenger_count_or_load_capacity")));
        return car;
    }

    private Car createTruck(final Map<String, Object> carLinesMap) {
        final Truck car = new Truck();
        car.setLoadCapacity(Integer.parseInt(
                (String) carLinesMap.get("passenger_count_or_load_capacity")));
        return car;
    }

    public Map<CarColors, Long> innerList(final List<List<Car>> listOfCarLists, final int lowerBoundOfPrice) {
        Optional.ofNullable(listOfCarLists)
                .orElseThrow(NullPointerException::new);
        if (lowerBoundOfPrice <= 0) {
            throw new IndexOutOfBoundsException("Your bound is less than or equal to 0!");
        }
        final CarComparator<Car> colorComparator = new CarComparator<>() {
            @Override
            public int compare(final Car firstCar, final Car secondCar) {
                return firstCar.getColor().compareTo(secondCar.getColor());
            }
        };

        Map<CarColors, Long> colorMap = listOfCarLists.stream()
                .flatMap(list -> list.stream())
                .sorted(colorComparator)
                .peek(System.out::println)
                .filter(car -> car.getPrice() > lowerBoundOfPrice)
                .map(Car::getColor)
                .collect(Collectors.groupingBy(identity(), Collectors.counting()));

        return colorMap;
    }

    public <T extends Car> Map<CarManufacturers, Integer> getManufacturersMap(final T[] cars) {
        Optional.ofNullable(cars)
                .orElseThrow(() -> new NullPointerException("Our repository is empty!"));

        final Map<CarManufacturers, Integer> carsManufacturersMap = new HashMap<>();

        for (T car : cars) {
            carsManufacturersMap.computeIfPresent(car.getManufacturer(), (k, v) -> ++v);
            carsManufacturersMap.computeIfAbsent(car.getManufacturer(), k -> 1);
        }

        return carsManufacturersMap;
    }

    public <T extends Car> Map<Engine, List<T>> getEnginesMap(final T[] cars) {
        Optional.ofNullable(cars)
                .orElseThrow(() -> new NullPointerException("Our repository is empty!"));

        final Map<Engine, List<T>> enginesMap = new HashMap<>();

        for (T car : cars) {
            enginesMap.computeIfPresent(car.getEngine(), (k, v) -> {
                v.add(car);
                return v;
            });

            enginesMap.computeIfAbsent(car.getEngine(), k -> {
                final List <T> engineCars = new ArrayList<>();
                engineCars.add(car);
                return engineCars;
            });
        }

        return enginesMap;
    }

    public void cleanRepository() {
        carArrayRepository.removeAll();
    }

    public void printManufacturerAndCount(final Car car) {
        final Optional<Car> optionalCar = Optional.ofNullable(car);

        optionalCar.ifPresent(car1 -> {
            System.out.println("The manufacturer of the car is: " + car1.getManufacturer());
            System.out.println("The count of cars: " + car1.getCount());
            System.out.println();
        });
    }

    public void printColor(final Car car) {
        final Car existentOrNewCar = Optional.ofNullable(car).orElse(createCar(CarTypes.CAR));

        System.out.println("The car with ID " + existentOrNewCar.getId() + " has " + existentOrNewCar.getColor() + " color");
        System.out.println();
    }

    public void checkCount(final Car car) {
        final Car rightCar = Optional.ofNullable(car)
                .filter(car1 -> car1.getCount() > 10)
                .orElseThrow(() -> new UserInputException("Your car isn't exist or it isn't suitable for condition!"));

        System.out.println("The car with ID: " + rightCar.getId());
        System.out.println("Manufacturer: " + rightCar.getManufacturer());
        System.out.println("Count: " + rightCar.getCount());
        System.out.println();
    }

    public void printEngineInfo(final Car car) {
        final Car existentOrNewCar = Optional.ofNullable(car)
                .orElseGet(() -> {
                    System.out.println("A new car has just been created");
                    return createCar(CarTypes.CAR);
                });
        System.out.println("ID of the car: " + existentOrNewCar.getId());

        final Engine engine = Optional.of(existentOrNewCar)
                .map(car1 -> car1.getEngine())
                .get();

        System.out.println("Power of the engine: " + engine.getPower());
        System.out.println();
    }

    public void printInfo(final Car car) {
        Optional.ofNullable(car).ifPresentOrElse(car1 -> print(car), () -> print(createCar(CarTypes.CAR)));
    }

    //  tested
    public int createRandomAmountOfCars(final RandomGenerator randomGenerator) {
        if (randomGenerator == null) {
            return -1;
        }

        int count = randomGenerator.getRandomNumber();
        if (count <= 0 || count > 10) {
            return -1;
        }

        for (int i = 0; i < count; i++) {
            int carTypeNumber = RANDOM.nextInt(2);

            if (carTypeNumber == 0) {
                createCar(CarTypes.CAR);
            } else {
                createCar(CarTypes.TRUCK);
            }
        }
        printAll();

        return count;
    }

    private CarManufacturers getRandomManufacturer() {
        CarManufacturers[] carManufacturers = CarManufacturers.values();
        int randomIndex = RANDOM.nextInt(carManufacturers.length);
        return carManufacturers[randomIndex];
    }

    private Engine getRandomEngine() {
        EngineTypes[] engineTypes = EngineTypes.values();
        int randomIndex = RANDOM.nextInt(engineTypes.length);
        EngineTypes engineType = engineTypes[randomIndex];
        int randomPower = RANDOM.nextInt(1001);
        return new Engine(randomPower, engineType);
    }

    private CarColors getRandomColor() {
        CarColors[] carColors = CarColors.values();
        int randomIndex = RANDOM.nextInt(carColors.length);
        return carColors[randomIndex];
    }

    private int getRandomPassengerCount() {
        return RANDOM.nextInt(4) + 2;
    }

    private int getRandomTruckCapacity() {
        return RANDOM.nextInt(3901) + 100;
    }

    private int getRandomCountOfCars() {
        return RANDOM.nextInt(100) + 1;
    }

    public <T extends Car> void setRandomCount(final T[] cars) {
        if (Optional.ofNullable(cars).isPresent()) {
            for (T car : cars) {
                car.setCount(getRandomCountOfCars());
            }
        }
    }

    public void replaceCarsFromRepository(final int indexOfFirst, final int indexOfSecond) {
        carArrayRepository.replaceCars(indexOfFirst, indexOfSecond);
    }

    //  tested
    public Car createCar(final CarTypes carType) {
        Car car;

        if (carType == null) {
            return null;
        }

        if (carType == CarTypes.CAR) {
            car = new PassengerCar(getRandomManufacturer(), getRandomEngine(), getRandomColor(), getRandomPassengerCount());
        } else {
            car = new Truck(getRandomManufacturer(), getRandomEngine(), getRandomColor(), getRandomTruckCapacity());
        }

        carArrayRepository.save(car);

        return car;
    }

    //  tested
    public void createCar(final int count, final CarTypes carType) {
        if (count <= 0 || carType == null) {
            return;
        }

        for (int i = 0; i < count; i++) {
            createCar(carType);
        }
    }

    //  tested
    public void insert(int index, final Car car) {
        carArrayRepository.insert(index, car);
    }

    //  tested
    public void printAll() {
        final Car[] allCars = carArrayRepository.getAll();

        if (allCars == null) {
            return;
        }

        for (int i = 0; i < allCars.length; i++) {
            System.out.println(allCars[i]);
        }
    }

    //  tested
    public Car[] getAll() {
        return carArrayRepository.getAll();
    }

    //  tested
    public Car find(final String id) {
        if (id == null || id.isBlank()) {
            return null;
        }
        return carArrayRepository.getById(id);
    }

    //  tested
    public void delete(final String id) {
        if (id == null || id.isBlank()) {
            return;
        }
        carArrayRepository.delete(id);
    }

    //  tested
    public void changeRandomColor(final String id) {
        if (id == null || id.isBlank()) {
            return;
        }

        final Car car = find(id);
        if (car == null) {
            return;
        }

        findAndChangeRandomColor(car);
    }

    private void findAndChangeRandomColor(final Car car) {
        final CarColors color = car.getColor();
        CarColors randomColor;

        do {
            randomColor = getRandomColor();
        } while (randomColor == color);
        carArrayRepository.updateColor(car.getId(), randomColor);
    }

    //  tested
    public Car createCar(final CarManufacturers manufacturer, final Engine engine, final CarColors color, final CarTypes carType) {
        Car car;

        if (manufacturer == null || engine == null || color == null || carType == null) {
            return null;
        }

        if (carType == CarTypes.CAR) {
            car = new PassengerCar(manufacturer, engine, color, getRandomPassengerCount());
        } else {
            car = new PassengerCar(manufacturer, engine, color, getRandomTruckCapacity());
        }

        carArrayRepository.save(car);
        return car;
    }

    public boolean carEquals(final Car firstCar, final Car secondCar) {

        if (Optional.ofNullable(secondCar).isPresent()) {
            Optional<Boolean> optionalAnswer = Optional.ofNullable(firstCar)
                    .filter(car1 -> car1.getType() == secondCar.getType())
                    .filter(car1 -> car1.hashCode() == secondCar.hashCode())
                    .filter(car1 -> car1.equals(secondCar))
                    .map(car -> true);

            return optionalAnswer.isPresent() ? optionalAnswer.get() : false;
        }

        return false;
    }

    //  tested
    public <T extends Car> void print(final T car) {
        Optional.ofNullable(car)
                .ifPresentOrElse(
                        car1 -> System.out.println(car1),
                        () -> System.out.printf("Error! Car isn't delivered.%n%n")
                );
    }

    //  tested
    public static void check(final Car car) {
        if (Optional.ofNullable(car).isEmpty()) {
            System.out.printf("Error! Car isn't delivered.%n%n");
        } else {

//          checks
            if (car.getCount() > 0 && car.getEngine().getPower() > 200) {
                System.out.println("The car is ready for sale. Car ID: " + car.getId());
            } else if (car.getCount() < 0) {
                System.out.println("The count is wrong. Car ID: " + car.getId());
            } else if (car.getEngine().getPower() < 200) {
                System.out.println("The power of the engine is wrong. Car ID: " + car.getId());
            } else {
                System.out.println("The count and the power of the engine are wrong. Car ID: " + car.getId());
            }
            System.out.println();
        }
    }

    public int compareCar(final Car firstCar, final Car secondCar) {
        return firstCar.getId().compareTo(secondCar.getId());
    }

    public static void main(String[] args) {
        final CarService carService = CarService.getInstance();
        carService.createRandomAmountOfCars(new RandomGenerator());

        System.out.println("~_~ ".repeat(20));
        System.out.println(carService.getManufacturersMap(carService.getAll()));

        System.out.println("~_~ ".repeat(20));
        System.out.println(carService.getEnginesMap(carService.getAll()));

        System.out.println("~_~ ".repeat(20));
        System.out.println(carService.carXmlToCarObject("xml/car.xml"));
        System.out.println(carService.carJsonToCarObject("json/car.json"));
    }
}
