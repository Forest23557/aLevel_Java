package com.shulha.repository;

import java.util.Optional;

public interface Repository<K, V> {
    void delete(V object);

    void save(K value);

    void removeAll();

    K[] getAll();

    Optional<K> getById(V object);
}
