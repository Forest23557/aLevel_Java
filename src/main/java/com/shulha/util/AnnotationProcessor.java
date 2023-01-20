package com.shulha.util;

import com.shulha.annotation.Autowired;
import com.shulha.annotation.Singleton;
import com.shulha.model.Car;
import com.shulha.repository.CarArrayRepository;
import com.shulha.repository.CarListRepository;
import com.shulha.repository.CarMapRepository;
import com.shulha.repository.Repository;
import lombok.SneakyThrows;
import org.reflections.Reflections;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Consumer;

public class AnnotationProcessor {
    private static final Reflections REFLECTIONS = new Reflections("com.shulha");
    private static final Map<String, Object> CACHE = new HashMap<>();
    private static AnnotationProcessor instance;

    private AnnotationProcessor() {
        findAndHandleSingletonClasses();
    }

    public static AnnotationProcessor getInstance() {
        instance = Optional.ofNullable(instance)
                .orElseGet(AnnotationProcessor::new);

        return instance;
    }

    public static Object getCachedObject(final String objectName) {
        return Optional.ofNullable(objectName)
                .map(name -> CACHE.get(name))
                .orElseThrow(NullPointerException::new);
    }

    public void findAndHandleSingletonClasses() {
        Set<Class<?>> typesAnnotatedWithSingleton = REFLECTIONS.getTypesAnnotatedWith(Singleton.class);
        if (typesAnnotatedWithSingleton.isEmpty()) {
            System.err.println("Don't have any classes with Singleton annotation");
        }
        typesAnnotatedWithSingleton.stream()
                .forEach(this::handleClass);
    }

    private Optional<Method> hasGetInstanceWithoutParameters(final Method[] declaredMethods) {
        return Arrays.stream(declaredMethods)
                .filter(method1 -> method1.getName() == "getInstance" && method1.getParameterCount() == 0)
                .findAny();
    }

    private Optional<Method> hasGetInstanceWithOneParameter(final Method[] declaredMethods) {
        return Arrays.stream(declaredMethods)
                .filter(method1 -> method1.getName() == "getInstance" && method1.getParameterCount() == 1)
                .findAny();
    }

    private void handleClass(final Class<?> someClass) {
        if (hasAutowired(someClass)) {
            handleAutowired(someClass);
        } else {
            handleSingleton(someClass);
        }
    }

    @SneakyThrows
    private void handleSingleton(final Class<?> someClass) {
        final Optional<Method> optionalMethod = hasGetInstanceWithoutParameters(someClass.getDeclaredMethods());

        if (optionalMethod.isPresent()) {
            final Method method = optionalMethod.get();
            CACHE.put(someClass.getSimpleName(), method.invoke(someClass));
        } else {
            final Constructor<?>[] constructors = someClass.getDeclaredConstructors();
            final Constructor<?> constructor = Arrays.stream(constructors)
                    .filter(constructor1 -> constructor1.getParameterCount() == 0)
                    .findAny()
                    .orElseThrow(IllegalStateException::new);

            constructor.setAccessible(true);
            CACHE.put(someClass.getSimpleName(), constructor.newInstance());
        }
    }

    @SneakyThrows
    private void handleAutowired(final Class<?> someClass) {
        Optional<Method> optionalMethod = hasGetInstanceWithOneParameter(someClass.getDeclaredMethods());

        if (optionalMethod.isPresent()) {
            final Method method = optionalMethod.get();
            final Autowired declaredAnnotation = method.getDeclaredAnnotation(Autowired.class);
            final Consumer<Object> repositoryConsumer = repository -> {
                try {
                    CACHE.put(someClass.getSimpleName(), method.invoke(someClass, repository));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                } catch (InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            };

            switch (declaredAnnotation.set()) {
                case CAR_MAP_REPOSITORY:
                    Optional.ofNullable(CACHE.get(CarMapRepository.class.getSimpleName()))
                            .ifPresentOrElse(
                                    repository -> repositoryConsumer.accept(repository),
                                    () -> {
                                        CACHE.put(CarMapRepository.class.getSimpleName(),
                                                CarMapRepository.getInstance());
                                        repositoryConsumer.accept(CACHE.get(CarMapRepository.class.getSimpleName()));
                                    }
                            );
                    break;
                case CAR_LIST_REPOSITORY:
                    Optional.ofNullable(CACHE.get(CarListRepository.class.getSimpleName()))
                            .ifPresentOrElse(
                                    repository -> repositoryConsumer.accept(repository),
                                    () -> {
                                        CACHE.put(CarListRepository.class.getSimpleName(),
                                                CarListRepository.getInstance());
                                        repositoryConsumer.accept(CACHE.get(CarListRepository.class.getSimpleName()));
                                    }
                            );
                    break;
                case CAR_ARRAY_REPOSITORY:
                    Optional.ofNullable(CACHE.get(CarArrayRepository.class.getSimpleName()))
                            .ifPresentOrElse(
                                    repository -> repositoryConsumer.accept(repository),
                                    () -> {
                                        CACHE.put(CarArrayRepository.class.getSimpleName(),
                                                CarArrayRepository.getInstance());
                                        repositoryConsumer.accept(CACHE.get(CarArrayRepository.class.getSimpleName()));
                                    }
                            );
                    break;
                case NULL:
                    repositoryConsumer.accept(null);
            }
        }
    }

    private boolean hasAutowired(final Class<?> someClass) {
        Method[] declaredMethods = someClass.getDeclaredMethods();
        return Arrays.stream(declaredMethods)
                .anyMatch(m -> Objects.nonNull(m.getDeclaredAnnotation(Autowired.class)));
    }

    public static void main(String[] args) {
        final AnnotationProcessor annotationProcessor = new AnnotationProcessor();
        annotationProcessor.findAndHandleSingletonClasses();
    }
}
