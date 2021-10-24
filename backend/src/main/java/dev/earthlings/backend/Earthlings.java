package dev.earthlings.backend;

import dev.earthlings.backend.javalin.StartupContext;

/**
 * Main class of the Earthlings Backend
 *
 * Calls on StartupContext so we don't have to work in a static environment
 */
public class Earthlings {

    public static void main(String[] args) {
        StartupContext startupContext = new StartupContext();

        startupContext.start();
    }
}
