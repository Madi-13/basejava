package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.model.Resume;

import java.util.LinkedHashMap;

public class MapStorage extends AbstractStorage {
    private final LinkedHashMap<String, Resume> storage = new LinkedHashMap<>();


    @Override
    protected void deleteResume(Object key) {
        String uuid = (String) key;
        storage.remove(uuid);
    }

    @Override
    protected Object getKey(String uuid) throws NotExistStorageException {
        if (!inStorage(new Resume(uuid))) {
            throw new NotExistStorageException(uuid);
        } else {
            return uuid;
        }
    }

    @Override
    protected Resume getResume(Object key) {
        String uuid = (String) key;
        return storage.get(uuid);
    }

    @Override
    protected void updateResume(Object key, Resume resume) {
        String uuid = (String) key;
        storage.put(uuid, resume);
    }

    @Override
    protected boolean inStorage(Resume resume) {
        return storage.containsKey(resume.getUuid());
    }

    @Override
    protected void saveResume(Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void clearStorage() {
        storage.clear();
    }

    @Override
    protected int getSize() {
        return storage.size();
    }

    @Override
    public Resume[] getAll() {
        return new Resume[0];
    }
}

