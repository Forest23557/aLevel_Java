package com.shulha.util;

import com.sun.source.tree.IfTree;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public class ConnectionPool {
    private static final int MAX_SIZE = 10;
    private static List<Connection> connectionList;
    private static Connection currentConnection;

    private ConnectionPool() {
    }

    private static List<Connection> createConnectionList() {
        connectionList = Optional.ofNullable(connectionList)
                .orElseGet(ArrayList::new);

        if (connectionList.size() == 0) {
            for (int i = 0; i < MAX_SIZE; i++) {
                connectionList.add(i, JdbcManager.getConnection());
            }
        }

        return connectionList;
    }

    @SneakyThrows
    public static void createCurrentConnection() {
        createConnectionList();

        currentConnection = Optional.ofNullable(currentConnection)
                .filter(connection1 -> {
                    try {
                        return !connection1.isClosed();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }).orElseGet(() -> connectionList.remove(connectionList.size() - 1));

        currentConnection.setAutoCommit(false);
    }

    public static Connection checkAndGetConnection(final AtomicBoolean existence) {
        currentConnection = Optional.ofNullable(currentConnection)
                .orElseGet(() -> {
                    existence.set(true);
                    createCurrentConnection();
                    return currentConnection;
                });

        return currentConnection;
    }

    public static Connection getCurrentConnection() {
        return currentConnection;
    }

    public static void commitAndClose() {
        Optional.ofNullable(currentConnection)
                        .ifPresent(connection -> {
                            try {
                                connection.commit();
                                connection.close();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });

        currentConnection = null;
    }

    public static void shutDownCurrentConnection() {
        Optional.ofNullable(currentConnection)
                        .ifPresent(connection -> {
                            try {
                                connection.close();
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        });

        currentConnection = null;
    }
}
