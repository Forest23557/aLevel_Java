package com.shulha.repository;

import com.shulha.annotation.Singleton;
import com.shulha.model.Engine;
import com.shulha.model.EngineTypes;
import com.shulha.util.JdbcManager;
import lombok.SneakyThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

@Singleton
public class EngineJdbcRepository implements Repository<Engine, String> {
    private static EngineJdbcRepository instance;
    private Connection connection;

    private EngineJdbcRepository() {
    }

    public static EngineJdbcRepository getInstance() {
        instance = Optional.ofNullable(instance)
                .orElseGet(EngineJdbcRepository::new);
        return instance;
    }

    protected void saveConnection(final Connection connection) {
        this.connection = connection;
    }

    @SneakyThrows
    protected void createTableInDB(final Connection connection) {
        final String sqlRequest = "CREATE TABLE IF NOT EXISTS \"engines\"(" +
                "\"id\" varchar(36) NOT NULL PRIMARY KEY, " +
                "\"power\" integer NOT NULL, " +
                "\"type\" varchar(50) NOT NULL" +
                ");";
        final PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);

        try {
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    @Override
    public void delete(final String id) {

    }

    @SneakyThrows
    @Override
    public void save(final Engine engine) {
        final AtomicBoolean isConnectionCreated = new AtomicBoolean(false);
        final String id = engine.getId();
        final String type = engine.getType().toString();
        final int power = engine.getPower();

        connection = Optional.ofNullable(connection)
                .orElseGet(() -> {
                    isConnectionCreated.set(true);
                    return JdbcManager.getConnection();
                });

        connection.setAutoCommit(false);

        final int count = checkIfEngineExists(id);

        if (count == 0) {
            final String sqlRequest = "INSERT INTO \"engines\" (\"id\", \"power\", \"type\") VALUES (?, ?, ?); ";
            final PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);

            preparedStatement.setString(1, id);
            preparedStatement.setInt(2, power);
            preparedStatement.setString(3, type);

            try {
                preparedStatement.executeUpdate();
                preparedStatement.close();
            } finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
            }
        }

        if (connection != null && isConnectionCreated.get()) {
            connection.commit();
            connection.close();
        }

    }

    @SneakyThrows
    private int checkIfEngineExists(final String engineId) {
        final String checkEngineExistenceSql = "SELECT COUNT(id) FROM \"engines\" WHERE engines.id = ?;";
        final PreparedStatement preparedStatement = connection.prepareStatement(checkEngineExistenceSql);

        preparedStatement.setString(1, engineId);

        final ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();

        int count = resultSet.getInt("count");

        try {
            preparedStatement.close();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }

        return count;
    }

    @Override
    public void removeAll() {

    }

    @SneakyThrows
    @Override
    public List<Engine> getAll() {
        final List<Engine> engineList = new ArrayList<>();
        final AtomicBoolean isConnectionCreated = new AtomicBoolean(false);

        connection = Optional.ofNullable(connection)
                .orElseGet(() -> {
                    isConnectionCreated.set(true);
                    return JdbcManager.getConnection();
                });
        connection.setAutoCommit(false);

        final String sqlRequest = "SELECT id AS \"engine_id\", type AS \"engine_type\", " +
                "power AS \"engine_power\" " +
                "FROM \"engines\";";
        final PreparedStatement preparedStatement = connection.prepareStatement(sqlRequest);
        final ResultSet resultSet = preparedStatement.executeQuery();

        final Map<String, List<Object>> engineStringsMap = getEngineStringsMap(resultSet);

        mapStringObjectToEngine(engineStringsMap, engineList);

        try {
            resultSet.close();
            preparedStatement.close();
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null && isConnectionCreated.get()) {
                connection.close();
            }
        }

        return engineList;
    }

    @SneakyThrows
    private List<Engine> mapStringObjectToEngine(final Map<String, List<Object>> engineStringsMap,
                                        final List<Engine> engineList) {

        final Iterator<String> iterator = engineStringsMap.keySet().iterator();
        final Class<Engine> engineClass = Engine.class;
        final Field idDeclaredField = engineClass.getDeclaredField("id");

        idDeclaredField.setAccessible(true);

        while (iterator.hasNext()) {
            final String id = iterator.next();
            final List<Object> objectList = engineStringsMap.get(id);

            final EngineTypes engineType = Enum.valueOf(EngineTypes.class, (String) objectList.get(0));
            final int enginePower = (int) objectList.get(1);
            final Engine engine = new Engine(enginePower, engineType);

            idDeclaredField.set(engine, id);

            engineList.add(engine);
        }

        return engineList;
    }

    @SneakyThrows
    private Map<String, List<Object>> getEngineStringsMap(final ResultSet resultSet) {
        final Map<String, List<Object>> engineStringsMap = new HashMap<>();

        while (resultSet.next()) {
            final List<Object> engineParametersList = new ArrayList<>();
            final String id = resultSet.getString("engine_id");
            final String engineType = resultSet.getString("engine_type");
            final Integer enginePower = resultSet.getInt("engine_power");

            engineParametersList.add(engineType);
            engineParametersList.add(enginePower);
            engineStringsMap.putIfAbsent(id, engineParametersList);
        }

        return engineStringsMap;
    }

    @Override
    public Optional<Engine> getById(final String id) {
        return Optional.empty();
    }

    @SneakyThrows
    public static void main(String[] args) {
        EngineJdbcRepository instance1 = EngineJdbcRepository.getInstance();
        List<Engine> all = instance1.getAll();
        System.out.println(all);
    }
}
