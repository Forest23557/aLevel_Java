package com.shulha.container;

import com.shulha.model.Car;

import java.util.Comparator;

public class CarComparator<E extends Car> implements Comparator<E> {
    @Override
    public int compare(final E currentElement, final E InsertingElement) {
        return currentElement.getCount() - InsertingElement.getCount();
    }
}
