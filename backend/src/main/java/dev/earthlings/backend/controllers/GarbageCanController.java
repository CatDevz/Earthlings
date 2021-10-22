package dev.earthlings.backend.controllers;

import dev.earthlings.backend.database.dao.Dao;
import dev.earthlings.backend.database.model.GarbageCan;
import dev.earthlings.backend.dto.GarbageCanCreateDto;
import dev.earthlings.backend.storage.FileStorage;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.BadRequestResponse;
import io.javalin.http.Context;
import io.javalin.http.NotFoundResponse;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class GarbageCanController implements CrudHandler {

    private final FileStorage garbageCanImageStorage;
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
    public void delete(@NotNull Context context, @NotNull String uuid) {
        Optional<GarbageCan> garbageCanOptional = garbageCanDao.find(garbageCan -> garbageCan.getUuid().equals(uuid));

        if (garbageCanOptional.isEmpty()) throw new NotFoundResponse();

        GarbageCan garbageCan = garbageCanOptional.get();
        garbageCanDao.remove(garbageCan);

        context.status(201);
    }

    @Override
    public void getAll(@NotNull Context context) {
        List<GarbageCan> garbageCanList = garbageCanDao.getAll();

        context.status(200);
        context.json(garbageCanList);
    }

    @Override
    public void getOne(@NotNull Context context, @NotNull String uuid) {
        Optional<GarbageCan> garbageCanOptional = garbageCanDao.find(garbageCan -> garbageCan.getUuid().equals(uuid));

        if (garbageCanOptional.isEmpty()) throw new NotFoundResponse();

        GarbageCan garbageCan = garbageCanOptional.get();

        context.status(200);
        context.json(garbageCan);
    }

    @Override
    public void update(@NotNull Context context, @NotNull String uuid) {
        // TODO: Add DTO & functionality
    }

    // TODO: Add url endpoint to get the image

}
