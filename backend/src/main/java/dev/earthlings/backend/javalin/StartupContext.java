package dev.earthlings.backend.javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

import dev.earthlings.backend.controllers.GarbageCanController;
import dev.earthlings.backend.database.dao.Dao;
import dev.earthlings.backend.database.dao.InMemoryDao;
import dev.earthlings.backend.database.model.GarbageCan;
import dev.earthlings.backend.storage.FileStorage;
import dev.earthlings.backend.storage.LocalFileStorage;
import io.javalin.Javalin;

import java.io.File;

public class StartupContext {

    public void start() {
        Javalin app = Javalin.create((config) -> {
            config.enableCorsForAllOrigins();
            config.defaultContentType = "application/json";
        }).start(8080);

        FileStorage garbageCanImageStorage = new LocalFileStorage(new File("tmp/images"));
        Dao<GarbageCan> garbageCanDao = new InMemoryDao<>();

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
