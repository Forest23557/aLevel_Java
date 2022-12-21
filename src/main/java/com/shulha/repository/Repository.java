package com.shulha.repository;

public interface Repository<K, V, I> {
    void delete(I object);

    void save(V value);

    void removeAll();

    V[] getAll();

    V getById(I object);

    void insert(K key, V value);
}
