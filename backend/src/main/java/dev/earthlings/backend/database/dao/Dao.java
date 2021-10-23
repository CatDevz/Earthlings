package dev.earthlings.backend.database.dao;

import dev.earthlings.backend.database.model.Model;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Dao<T extends Model> {

    void insert(T t);
    void update(String uuid, T t);
    void remove(T t);
    Optional<T> find(String uuid);
    List<T> getAll();

}