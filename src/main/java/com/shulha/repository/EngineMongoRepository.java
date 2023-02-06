package com.shulha.repository;

import com.mongodb.client.MongoDatabase;
import com.shulha.config.MongoUtil;

public class EngineMongoRepository {
    private static MongoDatabase mongoDatabase = MongoUtil.connect("mongodb");

    public static void main(String[] args) {
        EngineMongoRepository engineMongoRepository = new EngineMongoRepository();
    }
}
