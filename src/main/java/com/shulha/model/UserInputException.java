package com.shulha.model;

public class UserInputException extends IndexOutOfBoundsException {
    public UserInputException() {
    }

    public UserInputException(final String message) {
        super(message);
    }
}
