package com.shulha.container;

import com.shulha.model.Car;

import java.util.Optional;

public class CarList<E extends Car> {
    private final Iterator iterator = new Iterator();
    private int size = 0;
    private Node<E> first;
    private Node<E> last;

    private void linkFirst(final E element) {
        if (Optional.ofNullable(element).isPresent()) {
            final Node<E> first = this.first;
            final Node<E> newNode = new Node<>(null, element, first);

            this.first = newNode;
            if (first == null) {
                this.last = newNode;
            } else {
                first.previous = newNode;
            }
            size++;
        }
    }

    private void linkLast(final E element) {
        if (Optional.ofNullable(element).isPresent()) {
            final Node<E> last = this.last;
            final Node<E> newNode = new Node<>(last, element, null);

            this.last = newNode;
            if (last == null) {
                this.first = newNode;
            } else {
                last.next = newNode;
            }
            size++;
        }
    }

    private void unlinkElement(final Node<E> node) {
        final Node<E> next = node.next;
        final Node<E> prev = node.previous;

        if (prev == null && next == null && size == 1) {
            this.first = null;
            this.last = null;

        } else if (prev == null) {
            next.previous = null;
            this.first = next;

        } else if (next == null) {
            prev.next = null;
            this.last = prev;

        } else {
            prev.next = next;
            next.previous = prev;
        }
        size--;
    }

    private void insert(final E insertionElement, final Node<E> nodeAfter) {
        if (Optional.ofNullable(insertionElement).isPresent()) {
            final Node<E> prev = nodeAfter.previous;
            final Node<E> newInsertionNode = new Node<>(prev, insertionElement, nodeAfter);
            nodeAfter.previous = newInsertionNode;
            if (prev == null) {
                first = newInsertionNode;
            } else {
                prev.next = newInsertionNode;
            }
            size++;
        }
    }

    private Node<E> pointer(final int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException(size - 1);
        }

        int indexE;
        Node<E> currentNode;

        if ((size - index) > (size >> 1)) {
            indexE = 0;
            currentNode = this.first;

            for (; indexE < index; indexE++) {
                currentNode = currentNode.next;
            }
        } else {
            indexE = size - 1;
            currentNode = this.last;

            for (; indexE > index; indexE--) {
                currentNode = currentNode.previous;
            }
        }

        return currentNode;
    }

    private int searchIndex(final E element) {
        if (Optional.ofNullable(element).isPresent()) {
            int index = 0;
            Node<E> currentNode = first;

            while (currentNode.element != element && index < size) {
                if (currentNode.next == null) {
                    return -1;
                }
                currentNode = currentNode.next;
                index++;
            }

            if (index >= size) {
                return -1;
            }

            return index;
        }

        return -1;
    }

    private int totalCountOfCars() {
        int totalCount = 0;

        for (int i = 0; i < size; i++) {
            totalCount += pointer(i).element.getCount();
        }

        return totalCount;
    }

    public int totalCount() {
        return totalCountOfCars();
    }

    public void printAll() {
        for (int i = 0; i < size; i++) {
            System.out.println(pointer(i).element);
        }
    }

    public void remove(final int index) {
        final Node<E> node = pointer(index);
        unlinkElement(node);
    }

    public int getIndex(final E value) {
        return searchIndex(value);
    }

    public void insertByIndex(final int index, final E element) {
        final Node<E> elementAfterInsertion = pointer(index);
        insert(element, elementAfterInsertion);
    }

    public void add(final E element) {
        linkLast(element);
    }

    public void addToTheBeginning(final E element) {
        linkFirst(element);
    }

    public E get(final int index) {
        Node<E> nodeByTheIndex = pointer(index);
        return nodeByTheIndex.element;
    }

    public int size() {
        return size;
    }

    public boolean hasNext() {
        return iterator.hasNext();
    }

    public E next() {
        return iterator.next();
    }

    public void resetIterator() {
        iterator.reset();
    }

    private class Iterator {
        private Node<E> currentNode;
        private int index;

        public boolean hasNext() {
            if (index == 0) {
                currentNode = first;
                return (currentNode != null) ? true : false;
            }
            return (currentNode.next != null) ? true : false;
        }

        public E next() {
            if (index == 0) {
                currentNode = first;
            } else {
                currentNode = currentNode.next;
            }

            index++;
            return currentNode.element;
        }

        public void reset() {
            index = 0;
        }
    }

    private static class Node<E> {
        private E element;
        private Node<E> previous;
        private Node<E> next;

        public Node(final Node<E> previous, final E element, final Node<E> next) {
            this.previous = previous;
            this.element = element;
            this.next = next;
        }
    }

//    public static void main(String[] args) {
//        final CarList<Car> carList = new CarList<>();
//        CarService carService = CarService.getInstance();
//        carList.add(carService.createCar(CarTypes.CAR));
//        carList.add(null);
//        final Car car = carService.createCar(CarTypes.TRUCK);
//        car.setCount(10);
//        carList.add(car);
//        carList.add(carService.createCar(CarTypes.CAR));
//        carList.add(carService.createCar(CarTypes.CAR));
//        carList.addToTheBeginning(carService.createCar(CarTypes.CAR));
//        carList.insertByIndex(4, carService.createCar(CarTypes.CAR));
//        System.out.println(carList.get(0));
//        System.out.println(carList.get(5));
//        System.out.println("Size of the CarList: " + carList.size());
//        System.out.println("~_~ ".repeat(15));
//        carService.printAll();
//        carService.print(carList.get(0));
//        System.out.println("~_~ ".repeat(15));
//        System.out.println("Index of an existing car in the CarList: " + carList.getIndex(carService.getAll()[5]));
//        System.out.println("Index of null in the CarList: " + carList.getIndex(null));
//        carService.createCar(CarTypes.CAR);
//        System.out.println("Index of an existing car but it is not in the CarList: " + carList.getIndex(carService.getAll()[6]));

//        carList.printAll();
//        System.out.println("Size of the CarList: " + carList.size());
//        carList.remove(5);
//        System.out.println("~_~ ".repeat(15));
//        carList.printAll();
//        System.out.println("Size of the CarList: " + carList.size());
//        System.out.println("Total count of cars in the CarList: " + carList.totalCount());

//        System.out.println("~_~ ".repeat(15));
//        while (carList.hasNext()) {
//            System.out.println(carList.next());
//        }
//
//        carList.add(carService.createCar(CarTypes.CAR));
//        carList.add(carService.createCar(CarTypes.CAR));
//
//        carList.resetIterator();
//        System.out.println("~_~ ".repeat(15));
//        while (carList.hasNext()) {
//            System.out.println(carList.next());
//        }
//    }
}
