package com.shulha.repository;

import java.util.Optional;

public interface Repository<K, V, I> {
    void delete(I object);

    void save(V value);

    void removeAll();

    V[] getAll();

    Optional<V> getById(I object);
}
