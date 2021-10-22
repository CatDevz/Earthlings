package dev.earthlings.backend;

import dev.earthlings.backend.javalin.StartupContext;

public class Earthlings {

    public static void main(String[] args) {
        StartupContext startupContext = new StartupContext();

        startupContext.start();
    }
}
