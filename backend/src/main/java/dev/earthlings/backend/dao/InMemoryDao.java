package dev.earthlings.backend.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Collections.replaceAll;

public class InMemoryDao<T> implements Dao<T> {

    private List<T> storage = new ArrayList<>();

    @Override
    public void insert(T t) {
        storage.add(t);
    }

    @Override
    public void update(Predicate<T> predicate, T t) {
        Optional<T> exists = storage.stream().filter(predicate).findFirst();
        exists.ifPresent(value -> replaceAll(storage, value, t));
    }

    @Override
    public void remove(T t) {
        storage.remove(t);
    }

    @Override
    public Optional<T> find(Predicate<T> predicate) {
        return storage.stream().filter(predicate).findFirst();
    }

    @Override
    public List<T> getAll() {
        return storage;
    }

}
