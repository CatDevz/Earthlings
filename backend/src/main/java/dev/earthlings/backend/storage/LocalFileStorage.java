package dev.earthlings.backend.storage;

import lombok.RequiredArgsConstructor;

import java.io.*;

@RequiredArgsConstructor
public class LocalFileStorage implements FileStorage {

    private final File baseDirectory;

    @Override
    public void add(String name, byte[] bytes) throws IOException {
        File file = new File(baseDirectory, name);
        if (file.createNewFile()) {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(bytes);
        }
    }

    @Override
    public void delete(String name) {
        File file = new File(baseDirectory, name);
        file.delete();
    }

    @Override
    public byte[] get(String name) throws IOException {
        File file = new File(baseDirectory, name);
        FileInputStream fileInputStream = new FileInputStream(file);
        return fileInputStream.readAllBytes();
    }

}
