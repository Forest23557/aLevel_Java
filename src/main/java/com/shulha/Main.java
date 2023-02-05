package com.shulha;

import com.shulha.action.Actions;
import com.shulha.config.HibernateFactoryUtil;
import com.shulha.util.AnnotationProcessor;
import com.shulha.util.UserInput;
import lombok.SneakyThrows;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {
        final AnnotationProcessor annotationProcessor = AnnotationProcessor.getInstance();
        final Actions[] values = Actions.values();
        String[] names = mapActionToName(values);

        while (true) {
            final int userChoice = UserInput.menu(names);
            values[userChoice].execute();
        }
    }

    private static String[] mapActionToName(final Actions[] values) {
        String[] names = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            names[i] = values[i].getName();
        }
        return names;
    }
}
