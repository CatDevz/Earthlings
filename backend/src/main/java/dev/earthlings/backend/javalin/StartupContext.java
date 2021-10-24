package dev.earthlings.backend.javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

import dev.earthlings.backend.controllers.GarbageCanController;
import dev.earthlings.backend.database.dao.Dao;
import dev.earthlings.backend.database.dao.GarbageCanPostgresDao;
import dev.earthlings.backend.database.dao.InMemoryDao;
import dev.earthlings.backend.database.model.GarbageCan;
import dev.earthlings.backend.storage.FileStorage;
import dev.earthlings.backend.storage.LocalFileStorage;
import io.javalin.Javalin;
import org.sql2o.GenericDatasource;
import org.sql2o.Sql2o;

import javax.sql.DataSource;
import java.io.File;

public class StartupContext {

    public void start() {
        Javalin app = Javalin.create((config) -> {
            config.enableCorsForAllOrigins();
            config.defaultContentType = "application/json";
            config.maxRequestSize = 20_000_000L;
        }).start(8080);

        Sql2o sql2o = new Sql2o("jdbc:postgresql:earthlings", "cody", "");

        FileStorage garbageCanImageStorage = new LocalFileStorage(new File("tmp/images"));
        Dao<GarbageCan> garbageCanDao = new GarbageCanPostgresDao(sql2o, "pog.garbage_cans");

        // Creating our controllers
        GarbageCanController garbageCanController = new GarbageCanController(garbageCanImageStorage, garbageCanDao);

        // Defining our routes
        app.routes(() -> {
            path("cans", () -> {
                get(garbageCanController::getAll);
                post(garbageCanController::create);
                path("{uuid}", () -> {
                    get(garbageCanController::getOne);
                    delete(garbageCanController::delete);
                    get("image", garbageCanController::getImage);
                });
            });
        });
    }
}
