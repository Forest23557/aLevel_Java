package com.shulha.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<K, V> {
    void delete(V object);

    void save(K value);

    void removeAll();

    List<K> getAll();

    Optional<K> getById(V object);
}
