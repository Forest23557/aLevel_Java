package com.shulha.action;

import lombok.Getter;

@Getter
public enum Actions {
    CREATE("Create cars", new CreateAction());

    private final String name;
    private final CreateAction action;

    Actions(final String name, final CreateAction action) {
        this.name = name;
        this.action = action;
    }

    public void execute() {
        action.execute();
    }
}
