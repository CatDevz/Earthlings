package dev.earthlings.backend.database.dao;

import dev.earthlings.backend.database.model.Model;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class PostgresDao<T extends Model> implements Dao<T> {

    private final Sql2o sql2o;

    public PostgresDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public void insert(T t) {
        String queryString = "TODO";

        makeDatabaseCall(queryString, Function.identity());
    }

    @Override
    public void update(String uuid, T t) {
        String queryString = "TODO";

        makeDatabaseCall(queryString, query -> query.bind(t));
    }

    @Override
    public void remove(T t) {
        String queryString = "TODO";

        makeDatabaseCall(queryString, query -> query.addParameter("uuid", t.getUuid()));
    }

    @Override
    public Optional<T> find(String uuid) {
        String queryString = "TODO";

        return null; // TODO
    }

    @Override
    public List<T> getAll() {
        String queryString = "TODO";

        return null; // TODO
    }

    private void makeDatabaseCall(String queryString, Function<Query, Query> function) {
        CompletableFuture.runAsync(() -> {
            try (Connection connection = sql2o.open()) {
                Query query = function.apply(connection.createQuery(queryString));
                query.executeUpdate();
            }
        });
    }
}
