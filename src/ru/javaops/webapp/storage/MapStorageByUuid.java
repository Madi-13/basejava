package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.HashMap;

public class MapStorageByUuid extends MapStorage<String> {
    public MapStorageByUuid() {
        super(new HashMap<>());
    }

    @Override
    protected void deleteResume(String key) {
        storage.remove(key);
    }

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume getResume(String key) {
        return storage.get(key);
    }

    @Override
    protected void updateResume(String key, Resume resume) {
        storage.put(key, resume);
    }

    @Override
    protected boolean inStorage(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected void saveResume(String key, Resume resume) {
        storage.put(key, resume);
    }

}

