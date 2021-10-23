package dev.earthlings.backend.controllers;

import dev.earthlings.backend.database.dao.Dao;
import dev.earthlings.backend.database.model.GarbageCan;
import dev.earthlings.backend.dto.GarbageCanCreateDto;
import dev.earthlings.backend.storage.FileStorage;
import io.javalin.http.*;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
public class GarbageCanController {

    private final FileStorage garbageCanImageStorage;
    private final Dao<GarbageCan> garbageCanDao;

    public void getAll(@NotNull Context context) {
        List<GarbageCan> garbageCanList = garbageCanDao.getAll();

        context.status(200);
        context.json(garbageCanList);
    }

    public void getOne(@NotNull Context context) {
        String uuid = context.pathParam("uuid");
        Optional<GarbageCan> garbageCanOptional = garbageCanDao.find(garbageCan -> garbageCan.getUuid().equals(uuid));

        if (garbageCanOptional.isEmpty()) throw new NotFoundResponse();

        GarbageCan garbageCan = garbageCanOptional.get();

        context.status(200);
        context.json(garbageCan);
    }

    public void create(@NotNull Context context) {
        try {
            // Making sure the user provided a valid body
            GarbageCanCreateDto garbageCanCreateDto = context.bodyAsClass(GarbageCanCreateDto.class);
            if (garbageCanCreateDto == null) throw new BadRequestResponse();

            // Converting the create "form" to a usable database model
            //TODO: Move this into it's own class?
            String currentTime = LocalDateTime.now().toString();
            GarbageCan garbageCan = new GarbageCan(
                    UUID.randomUUID().toString(),
                    garbageCanCreateDto.getLatitude(),
                    garbageCanCreateDto.getLongitude(),
                    currentTime,
                    currentTime
            );

            // Inserting the database object
            garbageCanDao.insert(garbageCan);

            // Storing the decoded image into a file
            byte[] decodedPhoto = Base64.getDecoder().decode(garbageCanCreateDto.getPhotoBase64());
            garbageCanImageStorage.add(garbageCan.getUuid() + ".jpg", decodedPhoto);

            // Responding with status code 200 and json of the garbage can
            context.status(200);
            context.json(garbageCan);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerErrorResponse("Failed to save image");
        }
    }

    public void delete(@NotNull Context context) {
        String uuid = context.pathParam("uuid");
        Optional<GarbageCan> garbageCanOptional = garbageCanDao.find(garbageCan -> garbageCan.getUuid().equals(uuid));

        if (garbageCanOptional.isEmpty()) throw new NotFoundResponse();

        GarbageCan garbageCan = garbageCanOptional.get();
        garbageCanDao.remove(garbageCan);

        try {
            garbageCanImageStorage.delete(garbageCan.getUuid() + ".jpg");
        } catch (IOException ignored) {}

        context.status(201);
    }

    public void getImage(@NotNull Context context) {
        String uuid = context.pathParam("uuid");
        Optional<GarbageCan> garbageCanOptional = garbageCanDao.find(garbageCan -> garbageCan.getUuid().equals(uuid));

        if (garbageCanOptional.isEmpty()) throw new NotFoundResponse();

        try {
            byte[] image = garbageCanImageStorage.get(uuid + ".jpg");

            context.status(200);
            context.contentType(ContentType.IMAGE_JPG);
            context.result(image);
        } catch (IOException e) {
            e.printStackTrace();
            throw new InternalServerErrorResponse("Failed to read image");
        }
    }

}
