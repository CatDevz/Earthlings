package dev.earthlings.backend.dao;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class PostgresDao<T> implements Dao<T> {

    @Override
    public void insert(T t) {

    }

    @Override
    public void update(Predicate<T> predicate, T t) {

    }

    @Override
    public void remove(T t) {

    }

    @Override
    public Optional<T> find(Predicate<T> predicate) {
        return Optional.empty();
    }

    @Override
    public List<T> getAll() {
        return null;
    }

}
