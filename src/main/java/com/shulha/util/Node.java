package com.shulha.util;

import com.shulha.model.Car;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Node<E> {
    private E element;
    private Node<E> previous;
    private Node<E> next;

    public Node(final Node<E> previous,final E element, final Node<E> next) {
        this.previous = previous;
        this.element = element;
        this.next = next;
    }
}
