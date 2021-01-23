package ru.javaops.webapp.storage;

import ru.javaops.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected void deleteResume(Object key) {
        int index = (int) key;
        storage.remove(index);
    }

    @Override
    protected Integer getKey(String uuid) {
        for (int i = 0; i < storage.size(); i++) {
            if (storage.get(i).getUuid().equals(uuid)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    protected Resume getResume(Object key) {
        int index = (int) key;
        return storage.get(index);
    }

    @Override
    protected void updateResume(Object key, Resume resume) {
        int index = (int) key;
        storage.set(index, resume);
    }

    @Override
    protected boolean inStorage(Object key) {
        return (int) key >= 0;
    }

    @Override
    protected void saveResume(Resume resume, Object key) {
        storage.add(resume);
    }

    @Override
    protected void clearStorage() {
        storage.clear();
    }

    @Override
    protected int getSize() {
        return storage.size();
    }

    public Resume[] getAll() {
        return storage.toArray(new Resume[0]);
    }

}


