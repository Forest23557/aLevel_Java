package com.shulha.action;

public class ExitAction implements Action {
    @Override
    public void execute() {
        System.out.println("Goodbye!");
        System.exit(0);
    }
}
