package com.shulha;

import com.shulha.action.Actions;
import com.shulha.config.HibernateFactoryUtil;
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
