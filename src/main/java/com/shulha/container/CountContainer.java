package com.shulha.container;

import lombok.Getter;

public class CountContainer {
    @Getter
    private int count;

    public void increase(final int addingCount) {
        count += addingCount;
    }

    public void decrease(final int subtractingCount) {
        count += subtractingCount;
    }

    @Override
    public String toString() {
        return String.format("%d", count);
    }
}
