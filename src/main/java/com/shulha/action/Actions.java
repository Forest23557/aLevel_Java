package com.shulha.action;

import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public enum Actions {
    CREATE("Create cars", new CreateAction()),
    COMPARE("Compare cars", new CompareAction()),
    SHOW_ALL("Show all cars", new ShowAllAction()),
    FIND("Find a car by ID", new FindAction()),
    EXIT("Exit from the program", new ExitAction());

    private Logger logger;
    private final String name;
    private final Action action;

    Actions(final String name, final Action action) {
        this.name = name;
        this.action = action;
        this.logger = LoggerFactory.getLogger(action.getClass());
    }

    public void execute() {
        logger.info("{} method has been started!", name);
        action.execute();
        logger.info("{} method has been finished!", name);
    }
}
