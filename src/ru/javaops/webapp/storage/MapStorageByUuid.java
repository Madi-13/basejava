package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.LinkedHashMap;

public class MapStorageByUuid extends MapStorage<String> {
    public MapStorageByUuid() {
        super(new LinkedHashMap<>());
    }

    @Override
    protected void deleteResume(Object key) {
        storage.remove(key);
    }

    @Override
    protected String getKey(String uuid) {
        return uuid;
    }

    @Override
    protected Resume getResume(Object key) {
        return storage.get(key);
    }

    @Override
    protected void updateResume(Object key, Resume resume) {
        storage.put((String) key, resume);
    }

    @Override
    protected boolean inStorage(Object key) {
        return storage.containsKey(key);
    }

    @Override
    protected void saveResume(Object key, Resume resume) {
        storage.put((String) key, resume);
    }

}

