package dev.earthlings.backend.javalin;

import dev.earthlings.backend.dao.Dao;
import dev.earthlings.backend.dao.InMemoryDao;
import dev.earthlings.backend.model.GarbageCan;
import io.javalin.Javalin;

public class StartupContext {

    public void start() {
        Javalin app = Javalin.create().start(8080);
        app.get("/", ctx -> ctx.result("Hello Javalin"));

        Dao<GarbageCan> garbageCanDao = new InMemoryDao<>();

    }
}
