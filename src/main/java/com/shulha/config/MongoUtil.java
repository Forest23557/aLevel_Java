package com.shulha.config;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;

import java.util.Objects;

public class MongoUtil {
    private static MongoClient mongoClient;

    public static MongoDatabase connect(final String databaseName) {
        return getMongoClient(null).getDatabase(databaseName);
    }

    public static MongoDatabase connect(final String databaseName, final MongoCredential credential) {
        return getMongoClient(credential).getDatabase(databaseName);
    }

    public static void resetMongoClient() {
        if (Objects.nonNull(mongoClient)) {
            mongoClient.close();
            mongoClient = null;
        }
    }

    private static MongoClient getMongoClient(final MongoCredential credential) {
        if (Objects.isNull(mongoClient)) {
            final MongoClientOptions.Builder options = MongoClientOptions.builder();

            mongoClient = Objects.isNull(credential)
                    ? new MongoClient("localhost", 27017)
                    : new MongoClient(new ServerAddress("localhost", 27017), credential, options.build());
        }

        return mongoClient;
    }
}
