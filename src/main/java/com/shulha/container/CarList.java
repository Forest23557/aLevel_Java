package com.shulha.container;

import com.shulha.model.Car;
import com.shulha.util.Node;

public class CarList<E extends Car> {
    private int size = 0;
    private Node<E> first;
    private Node<E> last;

    private void setFirst(E e) {
        final Node<E> first = this.first;
        final Node<E> newNode = new Node<>(null, null, first);
        newNode.setElement(e);

        this.first = newNode;
        if (first == null){
            this.last = newNode;
        } else {
            first.setPrevious(newNode);
        }
        size++;
    }

    private void setLast(E e) {
        final Node<E> last = this.last;
        final Node<E> newNode = new Node<>(last, null, null);
        newNode.setElement(e);

        this.last = newNode;
        if (last == null){
            this.last = newNode;
        } else {
            last.setNext(newNode);
        }
        size++;
    }
}
