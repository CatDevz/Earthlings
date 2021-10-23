package dev.earthlings.backend.database.dao;

import dev.earthlings.backend.database.model.GarbageCan;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class GarbageCanPostgresDao implements Dao<GarbageCan> {

    private final Sql2o sql2o;
    private final String tableName;

    public GarbageCanPostgresDao(Sql2o sql2o, String tableName) {
        this.sql2o = sql2o;
        this.tableName = tableName;
    }

    @Override
    public void insert(GarbageCan t) {
        String queryString = "TODO";

        makeDatabaseCall(queryString, Function.identity());
    }

    @Override
    public void update(String uuid, GarbageCan t) {
        String queryString = "TODO";

        makeDatabaseCall(queryString, query -> query.bind(t));
    }

    @Override
    public void remove(GarbageCan t) {
        String queryString = String.format("DELETE FROM %s where uuid = :uuid", tableName);

        makeDatabaseCall(queryString, query -> query.addParameter("uuid", t.getUuid()));
    }

    @Override
    public Optional<GarbageCan> find(String uuid) {
        String queryString = String.format("SELECT * FROM %s WHERE uuid = :uuid", tableName);

        try (Connection connection = sql2o.open()) {
            return Optional.ofNullable(connection.createQuery(queryString)
                    .addParameter("uuid", uuid)
                    .executeAndFetchFirst(GarbageCan.class));
        }
    }

    @Override
    public List<GarbageCan> getAll() {
        String queryString = String.format("SELECT * FROM %s", tableName);

        try (Connection connection = sql2o.open()) {
            return connection.createQuery(queryString)
                    .executeAndFetch(GarbageCan.class);
        }    
    }

    private void makeDatabaseCall(String queryString, Function<Query, Query> function) {
        try (Connection connection = sql2o.open()) {
            Query query = function.apply(connection.createQuery(queryString));
            query.executeUpdate();
        }
    }

}

