package com.shulha.util;

import java.util.Random;

public class RandomGenerator {
    private static final Random RANDOM = new Random();

    public int getRandomNumber() {
        return RANDOM.nextInt(11);
    }
}
