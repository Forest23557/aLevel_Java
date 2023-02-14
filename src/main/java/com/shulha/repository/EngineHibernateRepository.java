package com.shulha.repository;

import com.shulha.annotation.Singleton;
import com.shulha.config.FlywayUtil;
import com.shulha.config.HibernateFactoryUtil;
import com.shulha.model.Engine;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Singleton
public class EngineHibernateRepository implements Repository<Engine, String> {
    private static final EntityManager ENTITY_MANAGER = HibernateFactoryUtil.getEntityManager();
    private static EngineHibernateRepository instance;

    private EngineHibernateRepository() {
    }

    public static EngineHibernateRepository getInstance() {
        FlywayUtil.getFlyway()
                .migrate();
        instance = Optional.ofNullable(instance)
                .orElseGet(EngineHibernateRepository::new);
        return instance;
    }

    @Override
    public void delete(final String id) {
        getById(id).ifPresent(engine -> {
            ENTITY_MANAGER.getTransaction().begin();
            ENTITY_MANAGER.remove(engine);
            ENTITY_MANAGER.getTransaction().commit();
        });
    }

    @Override
    public void save(final Engine engine) {
        if (Objects.nonNull(engine)) {
            ENTITY_MANAGER.getTransaction().begin();
            ENTITY_MANAGER.persist(engine);
            ENTITY_MANAGER.flush();
            ENTITY_MANAGER.getTransaction().commit();
        }
    }

    @Override
    public void removeAll() {
        ENTITY_MANAGER.getTransaction().begin();
        ENTITY_MANAGER.createNativeQuery("DELETE FROM engine")
                .executeUpdate();
        ENTITY_MANAGER.getTransaction().commit();
    }

    @Override
    public List<Engine> getAll() {
        return ENTITY_MANAGER.createQuery("select e from Engine e", Engine.class)
                .getResultList();
    }

    @Override
    public Optional<Engine> getById(final String id) {
        Engine engine = null;

        if (Objects.nonNull(id) && !id.isBlank()) {
            engine = ENTITY_MANAGER.find(Engine.class, id);
        }

        return Optional.ofNullable(engine);
    }
}
