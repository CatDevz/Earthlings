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

        createTable();
    }

    @Override
    public void insert(GarbageCan t) {
        String queryString = String.format("INSERT INTO %s (uuid, latitude, longitude, createdAt, updatedAt) " +
                "VALUES (:uuid, :latitude, :longitude, :createdAt, :updatedAt)", tableName);

        makeDatabaseCall(queryString, query -> query.bind(t));
    }

    @Override
    public void update(String uuid, GarbageCan t) {
        String queryString = String.format("UPDATE %s SET latitude = :latitude, longitude = :longitude, " +
                "updatedAt = :updatedAt WHERE uuid = :uuid", tableName);

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
        try {
            Connection connection = sql2o.open();
            connection.setRollbackOnException(false);
            Query query = function.apply(connection.createQuery(queryString));
            query.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void createTable() {
        String queryString = String.format("CREATE TABLE IF NOT EXISTS %s (uuid TEXT PRIMARY KEY UNIQUE, " +
                "latitude REAL, longitude REAL, createdAt TEXT, updatedAt TEXT)", tableName);

        makeDatabaseCall(queryString, Function.identity());
    }

}

