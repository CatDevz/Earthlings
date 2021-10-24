package dev.earthlings.backend.database.dao;

import dev.earthlings.backend.database.model.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Collections.replaceAll;

/**
 * In memory Dao used for testing purposes
 * @param <T> A model class, e.g GarbageCan
 */
public class InMemoryDao<T extends Model> implements Dao<T> {

    private List<T> storage = new ArrayList<>();

    @Override
    public void insert(T t) {
        storage.add(t);
    }

    @Override
    public void update(String uuid, T t) {
        Optional<T> exists = find(uuid);
        exists.ifPresent(value -> replaceAll(storage, value, t));
    }

    @Override
    public void remove(T t) {
        storage.remove(t);
    }

    @Override
    public Optional<T> find(String uuid) {
        return storage.stream().filter(t -> t.getUuid().equals(uuid)).findFirst();
    }

    @Override
    public List<T> getAll() {
        return storage;
    }

}
