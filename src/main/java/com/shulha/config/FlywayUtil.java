package com.shulha.config;

import com.shulha.util.JdbcManager;
import org.flywaydb.core.Flyway;

import java.util.Optional;

public class FlywayUtil {
    private static Flyway flyway;

    private FlywayUtil() {
    }

    public static Flyway getFlyway() {
        flyway = Optional.ofNullable(flyway)
                .orElseGet(() ->
                        Flyway.configure()
                                .dataSource("jdbc:postgresql://localhost:5432/Hibernate",
                                        "postgres", JdbcManager.getPassword())
                                .baselineOnMigrate(true)
                                .schemas("public")
                                .locations("db/migration")
                                .load()
                );

        return flyway;
    }
}
