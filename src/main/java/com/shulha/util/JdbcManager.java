package com.shulha.util;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

public class JdbcManager {
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/CarService?currentSchema=public";
    private static final String USER = "postgres";
    private static String PASS;

    private JdbcManager() {
    }

    public static String getPassword() {
        String password = "";
        final ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        try(final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(classLoader.getResourceAsStream("password.txt")))) {

            password = bufferedReader.readLine();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return password;
    }

    @SneakyThrows
    public static Connection getConnection() {
        PASS = JdbcManager.getPassword();
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

//    @SneakyThrows
//    public static void main(String[] args) {
//        Connection connection = JdbcManager.getConnection();
//        System.out.println(connection.getCatalog());
//        Statement statement = connection.createStatement();
//        final String statementString = "SELECT * FROM \"Orders\"";
//        boolean execute = statement.execute(statementString);
//        System.out.println(execute);
//        statement.close();
//
//        final String prepareStatementString = "SELECT * FROM \"Orders\"";
//        PreparedStatement preparedStatement = connection.prepareStatement(prepareStatementString);
//        boolean execute1 = preparedStatement.execute();
//        System.out.println(execute1);
//        preparedStatement.close();
//    }
}
