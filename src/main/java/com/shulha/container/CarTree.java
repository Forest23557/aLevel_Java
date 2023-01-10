package com.shulha.container;

import com.shulha.model.Car;
import lombok.Getter;

import java.util.Comparator;
import java.util.Optional;

public class CarTree<E extends Car> {
    private Comparator<E> carComparator;
    private Node<E> root;
    private int size;
    @Getter
    private int count;

    public CarTree() {
        this(new CarComparator<>());
    }

    public CarTree(final Comparator<E> carComparator) {
        this.carComparator = carComparator;
    }

    private static class Node<E> {
        private E element;
        private Node<E> left;
        private Node<E> right;

        public Node(final Node<E> left, final E element, final Node<E> right) {
            this.left = left;
            this.element = element;
            this.right = right;
        }
    }

    private void insert(final Node<E> currentNode, final E insertion) {
//      if the number is positive or equals 0 inserting element will go to the left side of the current element
//      and if the number is negative inserting element will go to the right side of the current element
        final int compare = carComparator.compare(currentNode.element, insertion);

        if (compare == 0) {
            Optional.ofNullable(currentNode.left)
                    .ifPresentOrElse(
                            left -> {
                                Node<E> temp = currentNode.left;
                                currentNode.left = new Node<>(temp, insertion, null);
                            },
                            () -> currentNode.left = new Node<>(null, insertion, null)
                    );

        } else if (compare > 0) {
            Optional.ofNullable(currentNode.left)
                    .ifPresentOrElse(
                            left -> insert(currentNode.left, insertion),
                            () -> currentNode.left = new Node<>(null, insertion, null)
                    );

        } else {
            Optional.ofNullable(currentNode.right)
                    .ifPresentOrElse(
                            right -> insert(currentNode.right, insertion),
                            () -> currentNode.right = new Node<>(null, insertion, null)
                    );
        }
    }

    public void add(final E car) {
        if (car != null) {
            if (root == null) {
                root = new Node<>(null, car, null);

            } else {
                insert(root, car);
            }

            count += car.getCount();
            size++;
        }
    }

    public int size() {
        return size;
    }

    private Node<E> searchByCount(final int count, final Node<E> currentNode) {
        Optional.ofNullable(currentNode)
                .orElseThrow(() -> new NullPointerException("Your element is not found!"));

        if (count < currentNode.element.getCount()) {
            return searchByCount(count, currentNode.left);
        } else if (count > currentNode.element.getCount()) {
            return searchByCount(count, currentNode.right);
        }

        return currentNode;
    }

    private Node<E> searchById(final int count, final String id, final Node<E> currentNode) {
        Optional.ofNullable(id)
                .filter(id1 -> !id1.isBlank())
                .orElseThrow(() -> new NullPointerException("Your id is wrong!"));

        if (currentNode.element.getId() == id) {
            return currentNode;
        } else if (currentNode.left != null && currentNode.left.element.getCount() == count) {
            return searchById(count, id, currentNode.left);
        }

        return null;
    }

    private Node<E> getNode(final int count, final String id) {
        if (count <= 0) {
            throw new IndexOutOfBoundsException("Your count is negative or 0!");
        }

        Optional.ofNullable(root)
                .orElseThrow(() -> new NullPointerException("CarTree is empty!"));

        final Node<E> searchedByCountNode = searchByCount(count, root);
        Node<E> searchedNodeOrNull = searchById(count, id, searchedByCountNode);

        final Node<E> searchedCarNode = Optional.ofNullable(searchedNodeOrNull)
                .orElseThrow(() -> new NullPointerException("Your element is not found!"));

        return searchedCarNode;
    }

    public E get(final int count, final String id) {
        return getNode(count, id).element;
    }

    private void print(final Node<E> node) {
        System.out.println(node.element);

        Optional.ofNullable(node.left)
                .ifPresent(left -> {
                    print(left);
                });

        Optional.ofNullable(node.right)
                .ifPresent(right -> {
                    print(right);
                });
    }

    public void printAll() {
        Optional.ofNullable(root)
                .orElseThrow(() -> new NullPointerException("CarTree is empty!"));

        print(root);
    }

//    public void remove(final int count, final String id) {
//        final Node<E> removingNode = getNode(count, id);
//
//        if (removingNode.left == null && removingNode.right == null) {
//            removingNode.element = null;
//
//        } else if (removingNode.left == null) {
//            final Node<E> insertingNode = removingNode.right;
//            removingNode.element = insertingNode.element;
//            removingNode.left = insertingNode.left;
//            removingNode.right = insertingNode.right;
//
//        } else if (removingNode.right == null) {
//            final Node<E> insertingNode = removingNode.left;
//            removingNode.element = insertingNode.element;
//            removingNode.left = insertingNode.left;
//            removingNode.right = insertingNode.right;
//
//        }
//
//        size--;
//    }
}
