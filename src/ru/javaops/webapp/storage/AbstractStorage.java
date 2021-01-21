package ru.javaops.webapp.storage;

import ru.javaops.webapp.exception.ExistStorageException;
import ru.javaops.webapp.exception.NotExistStorageException;
import ru.javaops.webapp.exception.StorageException;
import ru.javaops.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public Resume get(String uuid) throws NotExistStorageException {
        Object key = getKey(uuid);
        return getResume(key);
    }

    @Override
    public void update(Resume resume) throws NotExistStorageException {
        Object key = getKey(resume.getUuid());
        updateResume(key, resume);
    }

    @Override
    public void save(Resume resume) throws StorageException {
        if (inStorage(resume)) {
            throw new ExistStorageException(resume.getUuid());
        }
        saveResume(resume);
    }

    @Override
    public void delete(String uuid) throws NotExistStorageException {
        Object key = getKey(uuid);
        deleteResume(key);
    }

    @Override
    public void clear() {
        clearStorage();
    }

    @Override
    public int size() {
        return getSize();
    }

    protected abstract void deleteResume(Object key);

    protected abstract Object getKey(String uuid) throws NotExistStorageException;

    protected abstract Resume getResume(Object key);

    protected abstract void updateResume(Object key, Resume resume);

    protected abstract boolean inStorage(Resume resume);

    protected abstract void saveResume(Resume resume);

    protected abstract void clearStorage();

    protected abstract int getSize();
}
