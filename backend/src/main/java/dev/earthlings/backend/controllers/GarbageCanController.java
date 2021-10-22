package dev.earthlings.backend.controllers;

import dev.earthlings.backend.database.dao.Dao;
import dev.earthlings.backend.database.model.GarbageCan;
import dev.earthlings.backend.dto.GarbageCanCreateDto;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@RequiredArgsConstructor
public class GarbageCanController implements CrudHandler {

    private final Dao<GarbageCan> garbageCanDao;

    @Override
    public void create(@NotNull Context context) {
        GarbageCanCreateDto garbageCanCreateDto = context.bodyAsClass(GarbageCanCreateDto.class);

        if (garbageCanCreateDto == null) throw new BadRequestResponse();

        GarbageCan garbageCan = new GarbageCan(
                UUID.randomUUID().toString(),
                garbageCanCreateDto.getLatitude(),
                garbageCanCreateDto.getLongitude(),
                garbageCanCreateDto.getPhotoBase64(),
                "TODO", "TODO"
        );

        garbageCanDao.insert(garbageCan);

        context.status(200);
        context.json(garbageCan);
    }

    @Override
    public void delete(@NotNull Context context, @NotNull String s) {

    }

    @Override
    public void getAll(@NotNull Context context) {

    }

    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {

    }

    @Override
    public void update(@NotNull Context context, @NotNull String s) {

    }

}
