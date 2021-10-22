package dev.earthlings.backend;

import dev.earthlings.backend.dao.InMemoryDAO;
import dev.earthlings.backend.javalin.StartupContext;
import dev.earthlings.backend.model.GarbageCan;

public class Earthlings {

    public static void main(String[] args) {
        InMemoryDAO<GarbageCan> garbageCanDAO = new InMemoryDAO<>();

        StartupContext startupContext = new StartupContext();

        startupContext.start();
    }
}
