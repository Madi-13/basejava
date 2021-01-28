package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.LinkedHashMap;

public class MapStorageByResume extends MapStorage<String> {
    public MapStorageByResume() {
        super(new LinkedHashMap<>());
    }

    @Override
    protected void deleteResume(Object key) {
        storage.remove(((Resume) key).getUuid());
    }

    @Override
    protected Resume getKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected Resume getResume(Object key) {
        return (Resume) key;
    }

    @Override
    protected boolean inStorage(Object key) {
        return key != null;
    }

    @Override
    protected void updateResume(Object key, Resume resume) {
        storage.put(resume.getUuid(), (Resume) key);
    }

    @Override
    protected void saveResume(Object key, Resume resume) {
        storage.put(resume.getUuid(), (Resume) key);
    }

}

