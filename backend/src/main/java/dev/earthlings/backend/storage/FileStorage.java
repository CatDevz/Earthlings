package dev.earthlings.backend.storage;

import java.io.IOException;

public interface FileStorage {

    void add(String name, byte[] bytes) throws IOException;
    void delete(String name) throws IOException;
    byte[] get(String name) throws IOException;

}
