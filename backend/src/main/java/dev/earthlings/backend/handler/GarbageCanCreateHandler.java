package dev.earthlings.backend.handler;

import dev.earthlings.backend.dao.Dao;
import dev.earthlings.backend.model.GarbageCan;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import org.jetbrains.annotations.NotNull;

public class GarbageCanCreateHandler implements Handler {

    private final Dao<GarbageCan> garbageCanDao;

    public GarbageCanCreateHandler(Dao<GarbageCan> garbageCanDao) {
        this.garbageCanDao = garbageCanDao;
    }

    @Override
    public void handle(@NotNull Context ctx) {
        GarbageCan garbageCan = ctx.bodyAsClass(GarbageCan.class);

        if (garbageCan == null) {
            throw new BadRequestResponse();
        }

        garbageCanDao.insert(garbageCan);
    }
}
