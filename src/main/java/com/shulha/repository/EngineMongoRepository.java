package com.shulha.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.shulha.annotation.Singleton;
import com.shulha.config.MongoUtil;
import com.shulha.model.Engine;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class EngineMongoRepository implements Repository<Engine, String> {
    private static MongoDatabase mongoDatabase = MongoUtil.connect("mongodb");
    private static MongoCollection<Document> mongoDatabaseCollectionEngines = mongoDatabase.getCollection("engine");
    private static EngineMongoRepository instance;

    private EngineMongoRepository() {
    }

    public static EngineMongoRepository getInstance() {
        instance = Optional.ofNullable(instance)
                .orElseGet(EngineMongoRepository::new);
        return instance;
    }

    private String engineToJson(final Engine engine) {
        final Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .serializeNulls()
                .create();

        return gson.toJson(engine);
    }

    private Engine jsonToEngine(final String json) {
        return new Gson().fromJson(json, Engine.class);
    }

    @Override
    public void delete(final String id) {
        if (Objects.nonNull(id) && !id.isBlank()) {
            final Document filter = new Document();
            filter.append("id", id);

            mongoDatabaseCollectionEngines.findOneAndDelete(filter);
        }
    }

    private void update(final Engine engine) {
        final Document filter = new Document();
        filter.append("id", engine.getId());

        final Document newData = new Document();
        newData.append("type", engine.getType().toString());
        newData.append("power", engine.getPower());

        final Document updateEngine = new Document();
        updateEngine.append("$set", newData);

        mongoDatabaseCollectionEngines.updateOne(filter, updateEngine);
    }

    @Override
    public void save(final Engine engine) {
        if (Objects.nonNull(engine)) {
            final String id = engine.getId();

            getById(id).ifPresentOrElse(
                    engine1 -> {
                        update(engine);
                    },
                    () -> {
                        final String serializedEngine = engineToJson(engine);
                        final Document engineDocument = Document.parse(serializedEngine);

                        mongoDatabaseCollectionEngines.insertOne(engineDocument);
                    }
            );
        }
    }

    @Override
    public void removeAll() {
        mongoDatabaseCollectionEngines.drop();
    }

    @Override
    public List<Engine> getAll() {
        final List<Engine> engineList = new ArrayList<>();
        final FindIterable<Document> documentFindIterable = mongoDatabaseCollectionEngines.find();

        for (final Document document : documentFindIterable) {
            final String json = document.toJson();
            final Engine engine = jsonToEngine(json);
            engineList.add(engine);
        }

        return engineList;
    }

    @Override
    public Optional<Engine> getById(final String id) {
        Engine engine = null;

        if (Objects.nonNull(id) && !id.isBlank()) {
            final Document filter = new Document();
            filter.append("id", id);
            final FindIterable<Document> documentFindIterable =
                    mongoDatabaseCollectionEngines.find(filter);

            for (final Document document : documentFindIterable) {
                final String json = document.toJson();
                engine = jsonToEngine(json);
            }
        }

        return Optional.ofNullable(engine);
    }

//    public static void main(String[] args) {
//        final EngineMongoRepository engineMongoRepository = EngineMongoRepository.getInstance();
//        final Engine engine = new Engine(343, EngineTypes.MPFI);
//        engineMongoRepository.save(engine);
//        final String id = engineMongoRepository.getAll().get(0).getId();
//        System.out.println(id);
//        System.out.println(engineMongoRepository.getAll());
//        System.out.println("----".repeat(20));
//        engineMongoRepository.delete(id);
//        engineMongoRepository.removeAll();
//        System.out.println(engineMongoRepository.getAll());
//        System.out.println(engineMongoRepository.getById(id));
//        final Engine engine1 = engineMongoRepository.getById(id).get();
//        engine1.setPower(480);
//        engine1.setType(EngineTypes.CRDI);
//        engineMongoRepository.save(engine1);
//    }
}
