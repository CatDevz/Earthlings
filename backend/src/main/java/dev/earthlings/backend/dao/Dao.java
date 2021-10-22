package dev.earthlings.backend.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public interface Dao<T> {

    void insert(T t);
    void update(Predicate<T> predicate, T t);
    void remove(T t);
    Optional<T> find(Predicate<T> predicate);
    List<T> getAll();

}