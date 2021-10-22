package dev.earthlings.backend.javalin;

import io.javalin.Javalin;

public class StartupContext {

    public void start() {
        Javalin app = Javalin.create().start(8080);
        app.get("/", ctx -> ctx.result("Hello Javalin"));
    }
}
